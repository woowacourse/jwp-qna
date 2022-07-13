package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

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
        User save = userRepository.save(user);

        assertThat(user == save).isTrue();
    }

    @Test
    void findByUserId() {
        User user = new User("jurl", "1234", "주디", "dbswnfl2@");
        userRepository.save(user);

        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        assertThat(byUserId).hasValue(user);
    }
}
