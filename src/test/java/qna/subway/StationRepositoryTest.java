package qna.subway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;
    @Autowired
    private LineRepository lines;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stations.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stations.save(new Station(expected));
        String actual = stations.findByName(expected).orElseThrow().getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void saveWithLine() {
        Station expected = new Station("선릉역");
        Line line = lines.save(new Line("2호선"));
        expected.setLine(line);
        Station actual = stations.save(expected);
        stations.flush();
    }

    @Test
    void findByNameWithLine() {
        Station actual = stations.findByName("교대역").orElseThrow();
        assertThat(actual).isNotNull();
        assertThat(actual.getLine()).isNotNull();
    }

    @Test
    void updateWithLine() {
        Station expected = stations.findByName("교대역").orElseThrow();
        expected.setLine(lines.save(new Line("2호선")));
        stations.flush();

    }

    @Test
    void removeLineFromStation() {
        Station expected = stations.findByName("교대역").orElseThrow();
        expected.setLine(null);
        stations.flush();
    }

    @Test
    void removeLine() {
        Line line = lines.findByName("3호선").orElseThrow();
        Station expected = stations.findByName("교대역").orElseThrow();
        expected.setLine(null);
        lines.delete(line);
        lines.flush();
    }
}
