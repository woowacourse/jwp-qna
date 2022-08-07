package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.fixtures.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    private final UserRepository users;

    UserRepositoryTest(UserRepository users) {
        this.users = users;
    }

    @DisplayName("id가 일치하는 유저 조회")
    @Test
    void findByUserId() {
        User expect = UserFixture.JAVAJIGI.generate();
        users.save(expect);

        Optional<User> actual = users.findByUserId(expect.getUserId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expect)
        );
    }
}
