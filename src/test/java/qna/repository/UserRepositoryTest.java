package qna.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.User;
import qna.repository.config.RepositoryTestConfig;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 id로 조회한다.")
    void findByUserId() {
        // given
        final User expected = new User("hyena", "1234", "헤나", "test@test.com");
        userRepository.save(expected);

        // when
        Optional<User> actual = userRepository.findByUserId("hyena");

        // then
        assertThat(actual).contains(expected);
    }
}
