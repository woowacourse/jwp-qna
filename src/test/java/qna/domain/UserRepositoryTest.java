package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;

    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void save() {
        User user = new User("gray1234", "password", "gray", "gray@wooteco.com");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void findByUserId() {
        User user = new User("gray1234", "password", "gray", "gray@wooteco.com");
        User savedUser = userRepository.save(user);

        assertThat(userRepository.findByUserId(savedUser.getUserId())).isPresent();
    }
}
