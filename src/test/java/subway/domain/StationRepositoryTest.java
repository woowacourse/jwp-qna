package subway.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

	@Autowired
	private StationRepository stationRepository;

	@Test
	void save() {
		Station expected = new Station("잠실역");
		Station actual = stationRepository.save(expected);
		assertThat(actual.getId()).isNotNull();
		assertThat(actual.getName()).isEqualTo("잠실역");
	}

	@Test
	void findByName() {
		Station expected = stationRepository.save(new Station("잠실역"));
		Station actual = stationRepository.findByName("잠실역");

		assertThat(expected).isEqualTo(actual);
		assertThat(actual.getId()).isNotNull();
		assertThat(actual.getName()).isEqualTo("잠실역");
	}
}
