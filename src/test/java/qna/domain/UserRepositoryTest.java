package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId() {
        User savedUser = userRepository.save(JAVAJIGI);

        Optional<User> user = userRepository.findByUserId(savedUser.getUserId());
        assertThat(user).isPresent();
        User actual = user.get();
        assertThat(actual).isEqualTo(savedUser);
    }
}
