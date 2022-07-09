package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("사용자를 저장한다.")
    void save() {
        User expect = new User("userId", "password", "thor", "thor@gmail.com");
        User actual = users.save(expect);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getId()).isEqualTo(expect.getId())
        );
    }

    @Test
    @DisplayName("사용자의 식별자로 사용자를 조회한다.")
    void findById() {
        // given
        User testUser = new User("userId", "password", "thor", "thor@gmail.com");
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
