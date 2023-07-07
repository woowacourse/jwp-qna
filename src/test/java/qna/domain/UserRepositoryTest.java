package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class UserRepositoryTest extends RepositoryTest {

    private final UserRepository userRepository;

    UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void 아이디로_사용자를_찾을_수_있다() {
        // given
        final User expected = userRepository.save(UserTest.JAVAJIGI);

        // when
        final Optional<User> actual = userRepository.findByUserId(expected.getUserId());

        // then
        Assertions.assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected)
        );
    }
}
