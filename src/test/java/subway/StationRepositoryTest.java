package subway;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class StationRepositoryTest {
	@Autowired
	private StationRepository stations;
	private LineRespository lines;

	public StationRepositoryTest(StationRepository stations, LineRespository lines) {
		this.stations = stations;
		this.lines = lines;
	}

	@Test
	void save() {
		Station expected = new Station("잠실역");
		Station actual = stations.save(expected);
		Station actual2 = stations.save(new Station("잠실역")); // 나 왜 됨
		assertAll(
			() -> assertThat(actual.getId()).isNotNull(),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName())
		);
	}

	@Test
	void findByName() {
		String expected = "잠실역";
		stations.save(new Station(expected));
		String actual = stations.findByName(expected).getName();
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void identity(){ // 동등성이 아니라 동일성 테스트
		Station station1 = stations.save(new Station("잠실역"));
		Station station2 = stations.findById(station1.getId()).get();
		assertThat(station1 == station2).isTrue(); // 주소값을 냅다 비교
	}

	@Test
	void identity2(){
		Station station1 = stations.save(new Station("잠실역"));
		Station station2 = stations.findByName("잠실역");
		assertThat(station1 == station2).isTrue(); // 주소값을 냅다 비교
	}

	@Test
	void update() {
		Station station1 = stations.save(new Station("잠실역"));
		station1.changeName("선릉역");
		// station1.changeName("잠실역");
		Station station2 = stations.findByName("선릉역");
		assertThat(station2).isNotNull();
	}

	@Test
	void saveWithLine(){
		Station expected = new Station("선릉역");
		expected.setLine(lines.save(new Line("2호선")));
		Station actual = stations.save(expected);
		stations.flush();
	}

	@Test
	void findByNameWithLine(){
		Station actual = stations.findByName("교대역");
		assertThat(actual).isNotNull();
		assertThat(actual.getLine()).isNotNull();
	}

	@Test
	void updateWithLine(){
		Station expected = stations.findByName("교대역");
		expected.setLine(lines.save(new Line("2호선")));
		stations.flush();
	}

//	@Test
//	void removeLineInStation(){
//		Station expected = stations.findByName("교대역");
//		expected.setLine(null);
//		stations.flush();
//	}

//	@Test
//	void removeLine(){
//		Line line = lines.findByName("3호선");
//		Station expected = stations.findByName("교대역");
//		expected.setLine(null);
//		lines.delete(line); // 이 라인에 대한 모든 연관관계를 끊어준 후에 삭제해야 된다.
//		lines.flush();
//
//	}


}
