package subway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.*;


@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class LineRespositoryTest {
    private StationRepository stations;
    private LineRespository lines;

    public LineRespositoryTest(StationRepository stations, LineRespository lines) {
        this.stations = stations;
        this.lines = lines;
    }

    @Test
    void findByName(){
        Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void save(){
        Line line = new Line("2호선");
        line.addStation(stations.save(new Station("선릉역")));
        lines.save(line);
        lines.flush();
    }
}
