package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.JpaAuditingConfig;

@Import(JpaAuditingConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;

    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void save() {
        User user = new User("jurl", "1234", "주디", "dbswnfl2@");
        User actual = userRepository.save(user);

        assertThat(actual == user).isTrue();
    }

    @Test
    void findByUserId() {
        User user = new User("jurl", "1234", "주디", "dbswnfl2@");
        userRepository.save(user);

        Optional<User> actual = userRepository.findByUserId(user.getUserId());
        assertThat(actual).hasValue(user);
    }
}
