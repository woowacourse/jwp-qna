package qna.domain.repository;

import org.junit.jupiter.api.Test;
import qna.domain.User;
import qna.domain.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.Fixture.JAVAJIGI;


class UserRepositoryTest extends RepositoryTest {

    private final UserRepository userRepository;

    UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void 아이디로_사용자를_찾을_수_있다() {
        // given
        final User expected = userRepository.save(JAVAJIGI);

        // when
        final Optional<User> actual = userRepository.findByUserId(expected.getUserId());

        // then
        assertThat(actual).contains(expected);
    }
}
