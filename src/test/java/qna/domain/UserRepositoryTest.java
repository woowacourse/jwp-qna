package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class UserRepositoryTest {

    private final UserRepository userRepository;

    UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void 아이디로_사용자를_찾을_수_있다() {
        // given
        final User user = userRepository.save(UserTest.JAVAJIGI);

        // when
        final Optional<User> result = userRepository.findByUserId(user.getUserId());

        // then
        Assertions.assertAll(
                () -> assertThat(result).isPresent(),
                () -> assertThat(result.get()).usingRecursiveComparison().isEqualTo(user)
        );
    }
}
