package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void findByUserId() {
        User savedUser = userRepository.save(JAVAJIGI);

        Optional<User> user = userRepository.findByUserId(savedUser.getUserId());
        assertThat(user).isPresent();
        User actual = user.get();
        assertThat(actual).isEqualTo(savedUser);
    }
}
