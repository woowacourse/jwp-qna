package subway.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stations.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findByName() {
        Station expected = stations.save(new Station("잠실역"));
        Station actual = stations.findByName("잠실역");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void update() {
        Station station1 = stations.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        Station station2 = stations.findByName("몽촌토성역"); // name은 키값이기 때문에 영속성 컨텍스트에서 찾지 못한다.
        // id로 찾을 때만 영속성 컨텍스트에서 찾는다.
        // 객체의 값이 변경됐다는 것을 언제 어떻게 아나요?
        // flush()를 호출할 때 스냅샷과 엔티티를 비교한다.
        assertThat(station2).isNotNull();
        assertThat(station1).isEqualTo(station2);
        assertThat(station1.getId()).isEqualTo(station2.getId());
    }
    // 엔티티 매핑 미션 해보기!!

}
