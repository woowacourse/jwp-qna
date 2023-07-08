package subway;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

  @Autowired
  private StationRepository stationRepository;
  @Autowired
  private LineRepository lineRepository;

  @Test
  void save() {
    final Station expected = new Station("선릉");
    Station actual = stationRepository.save(expected);
    assertThat(actual.getId()).isNotNull();
    assertThat(actual.getName()).isEqualTo("선릉");
  }

  @Test
  void name() {
    stationRepository.save(new Station("선릉"));
    Station result = stationRepository.findByName("선릉");
    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("선릉");
  }

  @Test
  void identity() {
    Station station1 = stationRepository.save(new Station("잠실역"));
    Station station2 = stationRepository.findById(station1.getId()).get();
    assertThat(station1 == station2).isTrue();
  }

  @Test
  void identity2() {
    Station station1 = stationRepository.save(new Station(1L, "잠실역"));
    Station station2 = stationRepository.findByName("잠실역");
    assertThat(station1 == station2).isTrue();
  }

  @Test
  void update() {
    Station station1 = stationRepository.save(new Station("잠실역"));
    station1.changeName("선릉역");
    Station station2 = stationRepository.findByName("선릉역");
    assertThat(station2).isNotNull();
  }

  @Test
  void 정현승역() {
    Station station1 = stationRepository.save(new Station("정현승역"));
    station1.changeName("선릉역");
    Station station2 = stationRepository.findByName("선릉역");
    assertThat(station2).isNotNull();
  }

  @Test
  void saveWithLine() {
    Station station = new Station("선릉역");
    station.setLine(lineRepository.save(new Line("2호선")));

    final Station actual = stationRepository.save(station);
    stationRepository.flush();
  }


  @Test
  void findByNameWithLine() {
    Station actual = stationRepository.findByName("교대역");
    assertThat(actual).isNotNull();
  }

  @Test
  void updateWithLine() {
    Station expected = stationRepository.findByName("교대역");
    expected.setLine(lineRepository.save(new Line("2호선")));
    stationRepository.flush();
  }

  @Test
  void removeLineInStation() {
    Station expected = stationRepository.findByName("교대역");
    expected.setLine(null);
    stationRepository.flush();
  }

  @Test
  void removeLine() {
//        Line line = lineRepository.findByName("3호선");
//        lineRepository.delete(line);
//        lineRepository.flush();
  }

}
