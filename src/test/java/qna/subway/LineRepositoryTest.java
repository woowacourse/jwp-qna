package qna.subway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private StationRepository stations;
    @Autowired
    private LineRepository lines;


    @Test
    void findByName() {
        Line line = lines.findByName("3호선").orElseThrow();
        System.out.println("===============");
        assertThat(line.getStations()).hasSize(1);
        System.out.println("===============");
    }

    @Test
    void save() {
        Line line = new Line("2호선");
        line.addStation(stations.save(new Station("선릉")));
        lines.save(line);
        lines.flush();
    }
}
