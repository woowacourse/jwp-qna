package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.utils.fixture.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    private UserRepository users;

    public UserRepositoryTest(UserRepository users) {
        this.users = users;
    }

    @Test
    @DisplayName("사용자를 저장한다.")
    void save() {
        User expect = UserFixture.JAVAJIGI;
        User actual = users.save(expect);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("사용자의 식별자로 사용자를 조회한다.")
    void findById() {
        // given
        User testUser = UserFixture.JAVAJIGI;
        User savedUser = users.save(testUser);

        // when
        Optional<User> foundUser = users.findById(savedUser.getId());

        // then
        assertAll(
                () -> assertThat(foundUser).isNotEmpty(),
                () -> assertThat(testUser)
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(testUser)
        );
    }
}
