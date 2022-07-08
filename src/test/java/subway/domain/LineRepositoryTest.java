package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lines;

    @Autowired
    private StationRepository stations;

    @Test // 핵심: flush 전에 모든 연관관계를 전부 영속 상태로 만들어줘야 함!
    void saveWithLine() {
        Station expected = new Station("잠실역");
        Line line = new Line("2호선");
        expected.setLine(line);
        Station actual = stations.save(expected);
        lines.save(line);

        // 테스트에서는 강제적으로 flush 필요! 디폴트로 굳이 실행하지 않음!
        stations.flush(); // transaction commit
    }

    @Test // @ManyToOne(optional = false) : eager loading. 필요하든지 말든지 일단 SELECT
    void findByNameWithLine() {
        Station actual = stations.findByName("교대역");
        assertThat(actual).isNotNull();
        // assertThat(actual.getLine().getName()).isEqualTo("3호선"); // optional = true면 무조건 필요할 때만 지연 로드
    }

    @Test
    void updateWithLine() {
        Station expected = stations.findByName("교대역");
        expected.setLine(lines.save(new Line("2호선")));
        // line_id와 name 컬럼 모두 UPDATE로 수정됨.
        // 디폴트: dirty checking으로 식별자/PK 이외의 모든 값 수정

        stations.flush(); // transaction commit
    }

    @Test // line에 대한 연관관계만 제거
    void removeLineInStation() {
        final Station actual = stations.findByName("교대역");
        actual.setLine(null);

        stations.flush(); // transaction commit
    }

    @Test // line을 직접적으로 제거하려면 연관관계들 전부 해결해야 함.
    void removeLine() {
        Line line = lines.findByName("3호선");
        lines.delete(line);
        stations.flush(); // transaction commit
    }

    @Test
    void findByName() {
        Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void save() {
        Line line = lines.findByName("3호선");
        Station 잠실역 = stations.save(new Station("잠실역"));
        line.addStation(잠실역);
        lines.save(line);
        lines.flush();
    }
}
