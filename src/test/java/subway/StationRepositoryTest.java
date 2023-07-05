package subway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // autowired 사용없이 하는법
@DataJpaTest
class StationRepositoryTest {

    private StationRepository stations; // 컬렉셜과 역할이 비슷함. 컬렉션 친화적임

    public StationRepositoryTest(final StationRepository stations) {
        this.stations = stations;
    }

    @Test
    void save() {
        final Station expected = new Station("잠실역");
        final Station actual = stations.save(expected);
        // final Station actual2 = stations.save(new Station("잠실역"));
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        stations.save(new Station("잠실역"));
        final Station actual = stations.findByName("잠실역");
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void identity() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findById(station1.getId()).get(); // 쿼리문이 나오지 않는 이유 : 1차 캐시에 저장되어 있음
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void identity2() {
        final Station station1 = stations.save(new Station("잠실역"));
        final Station station2 = stations.findByName("잠실역");
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void update() {
        final Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("선릉역");
        final Station station2 = stations.findByName("선릉역");
        assertThat(station2).isNotNull();
    }
}
