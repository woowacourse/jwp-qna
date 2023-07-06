package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 계정_ID를_이용하여_조회한다() {
        // given
        String userId = "javajigi";
        User user = new User(1L, userId, "password", "name", "javajigi@slipp.net");
        userRepository.save(user);

        // when
        Optional<User> result = userRepository.findByUserId(userId);
        
        // then
        assertThat(result).isPresent();
    }
}
