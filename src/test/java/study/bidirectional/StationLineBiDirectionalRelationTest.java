package study.bidirectional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StationLineBiDirectionalRelationTest {

    @Autowired
    private BiDirectionalStationRepository stations;

    @Autowired
    private BiDirectionalLineRepository lines;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 양방향으로_서로_참조하여_조회_가능() {
        jdbcTemplate.execute("INSERT INTO bi_line (id, name) VALUES (1, '3호선')");
        jdbcTemplate.execute("INSERT INTO bi_station (id, bi_line_id, name) VALUES (1, 1, '교대역')");

        BiDirectionalLine line = lines.findById(1L).get();
        List<BiDirectionalStation> stations = line.getStations();

        assertThat(stations.size()).isEqualTo(1);
        assertThat(stations.get(0).getName()).isEqualTo("교대역");
        assertThat(stations.get(0).getBiLine().getName()).isEqualTo("3호선");
    }

    /**
     * 외래키를 관리하지 않는 쪽에 연관관계를 추가하여 저장하는 경우 연관관계 생성 불가
     * <p>
     * addStation()을 연관관계 편의 메서드로 만들어, station에서도 해당 line을 참조하도록 설정하면 저장됨.
     */
    @Test
    void saveStationAtLine() {
        BiDirectionalLine line = new BiDirectionalLine("2호선");
        line.addStation(stations.save(new BiDirectionalStation("잠실역"))); // id=1, name='잠실역', bi_line_id=null
        lines.save(line); // id=1, name='2호선' // bi_line_id(FK)가 없으므로 저장해도 영향 없음
        lines.flush();
    }
}
