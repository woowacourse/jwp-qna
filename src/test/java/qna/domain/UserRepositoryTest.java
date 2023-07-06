package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId() {
        User user = new User("userId", "password", "name", "email");
        userRepository.save(user);
        User findUser = userRepository.findByUserId(user.getUserId()).get();

        assertThat(findUser).isNotNull();
    }
}
