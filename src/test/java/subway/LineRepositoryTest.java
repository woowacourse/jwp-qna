package subway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class LineRepositoryTest {

  private final StationRepository stationRepository;
  private final LineRepository lineRepository;

  public LineRepositoryTest(StationRepository stationRepository, LineRepository lineRepository) {
    this.stationRepository = stationRepository;
    this.lineRepository = lineRepository;
  }

  @Test
  void findByName() {
    Line line = lineRepository.findByName("3호선");
    Assertions.assertThat(line.getStations()).isNotNull();
  }

  @Test
  void save() {
    Line line = new Line("2호선");
    line.addStation(stationRepository.save(new Station("선릉역")));
    lineRepository.save(line);
    lineRepository.flush();
  }
}
