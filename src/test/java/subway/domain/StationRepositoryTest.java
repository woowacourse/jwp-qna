package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        final Station expected = new Station("잠실역");
        System.out.println(expected); // Station{id=null, name='잠실역'}
        final Station actual = stations.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        System.out.println(actual); // Station{id=1, name='잠실역'}
        System.out.println(expected); // Station{id=1, name='잠실역'}
    }

    @Test
    void findByName() {
        final Station expected = stations.save(new Station("잠실역"));
        stations.findById(expected.getId()).get(); // SELECT 실행X : 식별자에 대응되는 엔티티가 이미 1차 캐시에 존재
        final Station actual2 = stations.findByName("잠실역"); // SELECT 실행O

        assertThat(actual2.getId()).isNotNull();
        assertThat(actual2.getName()).isEqualTo("잠실역");
        assertThat(actual2).isSameAs(expected); // 동일성: 1차 캐시의 값. 트랜잭션 내에서는 equals/hashcode 재구현할 필요 없음.
    }

    @Test
    void identity() {
        Station stations1 = stations.save(new Station("잠실역"));
        Station stations2 = stations.findById(stations1.getId()).get(); // SELECT 실행X : 식별자에 대응되는 엔티티가 이미 1차 캐시에 존재
        assertThat(stations1 == stations2).isTrue();
    }

    @Test
    void write() {
        stations.save(new Station("잠실역"));
    }

    @Test
    void write2() {
        stations.save(new Station("잠실역"));
        stations.save(new Station("선릉역"));
        stations.save(new Station("강남역"));
        System.out.println("################");
        stations.flush();
    }

    @Test // dirty checking
    void update() {
        Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역"); // (2) flush()를 통해 영속성 컨텍스트와 DB를 동기화시키게 됨. 스냅샷과 현재 값이 다르므로 Update 실행.
        // (1) name은 식별자가 아니므로 영속성 컨텍스트를 거치지 않고, 일단 DB에 접근!
        Station station2 = stations.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }


    @Test // dirty checking
    void update2() {
        Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        station1.changeName("잠실역"); // (2) 스냅샷과 현재 상태 동일하므로 Update 미실행.
        // (1) name은 식별자가 아니므로 영속성 컨텍스트를 거치지 않고, 일단 DB에 접근!
        Station station2 = stations.findByName("잠실역");
        assertThat(station2).isNotNull();
    }
}
