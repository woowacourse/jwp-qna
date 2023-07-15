package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class IdentityEqualityTest {

    private final TestEntityManager entityManager;

    public IdentityEqualityTest(final TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @DisplayName("equals&hashcode를 재정의하여 준영속 상태, 영속 상태의 두 엔티티 간 동등성을 보장한다.")
    @Test
    void persistAndDetached() {
        User user = new User("dy1007", "1234", "doy", "doy@gmail.com");
        final Object id = entityManager.persistAndGetId(user);

        final User find1 = entityManager.find(User.class, id);
        // find1: 준영속 상태
        entityManager.detach(user);

        // find2: 영속 상태
        final User find2 = entityManager.find(User.class, id);

        assertThat(find1.hashCode()).isEqualTo(find2.hashCode());
        assertThat(find1).isEqualTo(find2); // PK에 따라 동등성 보장
        assertThat(find1).isNotSameAs(find2); // 하지만 참조값은 다르다.
    }
}
