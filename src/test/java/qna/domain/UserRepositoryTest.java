package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("유저를 저장한다")
    void save() {
        User expect = new User("rookie_id", "password", "rookie", "rookie@gmail.com");
        User actual = users.save(expect);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(expect).isEqualTo(actual)
        );
    }

    @Test
    @DisplayName("사용자의 식별자로 사용자를 조회한다.")
    void findById() {
        // given
        User user = new User("rookie_id", "password", "rookie", "rookie@gmail.com");
        User savedUser = users.save(user);

        // when
        Optional<User> findUser = users.findById(savedUser.getId());

        // then
        assertAll(
            () -> assertThat(findUser).isNotEmpty(),
            () -> assertThat(savedUser).isEqualTo(findUser.get())
        );
    }
}
