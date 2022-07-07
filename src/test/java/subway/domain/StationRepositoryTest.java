package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        // given
        Station station = new Station("잠실역");
        Station actual = stationRepository.save(station);

        // when, then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        // given
        Station station = new Station("잠실역");
        stationRepository.save(station);

        // when
        Station actual = stationRepository.findByName("잠실역");

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
        assertThat(station).isEqualTo(actual);
        assertThat(station).isSameAs(actual);
    }

    @Test
    void update() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        station1.changeName("성수역");
        Station station2 =  stationRepository.findByName("성수역");

        assertThat(station2).isNotNull();
    }
}
