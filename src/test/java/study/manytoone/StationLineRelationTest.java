package study.manytoone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationLineRelationTest {

    @Autowired
    private StationRepository stations;

    @Autowired
    private LineRepository lines;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nested
    class SaveTest {

        @Test
        void JPA에서_엔티티를_저장할_때_연관된_모든_엔티티는_영속_상태여야_한다() {
            Station expected = new Station("잠실역");
            Line line = new Line("2호선");
            expected.setLine(line);
            // station의 line 필드값은 아직 영속성 컨텍스트에 관리되지 않으므로 플러시하면 저장되지 않음

            Station actual = stations.save(expected);
            lines.save(line); // flush 이전 아무때나 모든 연관관계를 전부 영속 상태로 만들어줘야 함!
            stations.flush(); // transaction commit

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 영속_상태가_아닌_엔티티를_연관관계로_설정하여_저장_시도시_영속성_컨텍스트가_인식하지_못하므로_예외_발생() {
            Station station = new Station("잠실역");
            station.setLine(new Line("2호선"));

            // station의 line 필드값은 영속성 컨텍스트에 관리되지 않으므로 플러시해도 저장되지 않음
            // 예외 메시지: object references an unsaved transient instance - save the transient instance before flushing
            stations.save(station);

            // 테스트에서는 강제적으로 flush 필요! 롤백 예정이면 디폴트로 굳이 실행하지 않음!
            assertThatThrownBy(() -> stations.flush())
                    .isInstanceOf(DataAccessException.class);
        }
    }

    /**
     * 기본적으로 eager fetch가 디폴트이므로 지하철역만 조회하는 경우 노선에 대한 SELECT도 즉시 실행됨.
     * <p>
     * 다만, @ManyToOne(fetch = FetchType.LAZY)을 통해 lazy fetch를 설정하는 경우, 지하철역 조회시 노선은 아직 조회되지 않음. 향후 노선 필드를 필요로 할 때
     * SELECT문 발생.
     */
    @Test
    void findStationWithoutLine() {
        jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (1, '3호선')");
        jdbcTemplate.execute("INSERT INTO station (id, line_id, name) VALUES (1, 1, '교대역')");

        stations.findByName("교대역");
    }

    /**
     * 디폴트로 플러시 시점에 엔티티의 현재 값과 스냅샷 사이에 조금이라도 차이가 있으면 해당 엔티티의 모든 필드에 대해 UPDATE문 실행됨.
     * <p>
     * 연관관계가 아닌 필드가 수정되는 경우 LAZY FETCH면 `line_id` 등의 FK 컬럼 값을 그대로 사용하여 수정. SELECT 미실행.
     * <p>
     * 영속 상태의 엔티티만 새로운 연관관계로 수정 가능
     */
    @Nested
    class UpdateTest {

        @BeforeEach
        void setUp() {
            jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (1, '3호선')");
            jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (2, '9호선')");
            jdbcTemplate.execute("INSERT INTO station (id, line_id, name) VALUES (1, 1, '교대역')");
        }

        @Test
        void 연관관계가_아닌_필드_수정시_LAZY_FETCH인_필드는_SELECT_실행_없이_FK_값_그대로_사용하여_UPDATE_실행() {
            Station expected = stations.findByName("교대역");
            expected.changeName("잠실역");
            stations.flush();
        }

        @Test
        void 영속_상태의_엔티티만_새로운_연관관계로_수정_가능__새로_저장() {
            Station expected = stations.findByName("교대역");
            expected.setLine(lines.save(new Line("2호선"))); // INSERT 후 UPDATE
            stations.flush();
        }

        @Test
        void 영속_상태의_엔티티만_새로운_연관관계로_수정_가능__이미_존재하는_다른_엔티티_활용() {
            Station expected = stations.findByName("교대역");
            expected.setLine(lines.findById(2L).get()); // SELECT 후 UPDATE
            stations.flush();
        }

        @Test
        void 연관관계를_수정하려는_경우_영속성_컨텍스트에_존재하지_않으면_예외_발생() {
            Station expected = stations.findByName("교대역");
            expected.setLine(new Line("2호선"));
            assertThatThrownBy(() -> stations.flush())
                    .isInstanceOf(DataAccessException.class);
        }
    }

    /**
     * 지하철역(1)은 독립적으로 제거 가능
     * <p>
     * 노선(N)은 FK 제약조건 때문에 연관관계를 먼저 전부 제거한 이후에 제거 가능.
     * <p>
     * 즉 연관된 지하철역의 line_id를 전부 null로 만들거나, 지하철역 자체를 제거한 이후에 노선 제거 가능.
     */
    @Nested
    class DeleteTest {

        @Test
        void removeLineFromStation() {
            jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (1, '3호선')");
            jdbcTemplate.execute("INSERT INTO station (id, line_id, name) VALUES (1, 1, '교대역')");

            Station expected = stations.findByName("교대역");
            expected.setLine(null);
            stations.flush();
        }

        @Test
        void removeLineWithStationsRemaining() {
            jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (1, '3호선')");
            jdbcTemplate.execute("INSERT INTO station (id, line_id, name) VALUES (1, 1, '교대역')");

            lines.deleteAll();
            assertThatThrownBy(() -> stations.flush())
                    .isInstanceOf(DataAccessException.class);
        }

        @Test
        void removeLineAfterRemovingRelationshipWithStation() {
            jdbcTemplate.execute("INSERT INTO line (id, name) VALUES (1, '3호선')");
            jdbcTemplate.execute("INSERT INTO station (id, line_id, name) VALUES (1, 1, '교대역')");

            Station expected = stations.findByName("교대역");
            expected.setLine(null);
            lines.deleteAll();
            stations.flush();
        }
    }
}
