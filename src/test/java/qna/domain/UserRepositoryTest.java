package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @BeforeEach
    void setUp() {
        users.deleteAll();
    }

    @DisplayName("사용자를 저장한다.")
    @Test
    void save() {
        User user = new User("알린", "12345678", "장원영", "ozragwort@gmail.com");

        User actual = users.save(user);

        assertThat(actual == user).isTrue();
    }

    @DisplayName("userId로 사용자를 찾는다.")
    @Test
    void findByUserId() {
        User user = users.save(new User("알린", "12345678", "장원영", "ozragwort@gmail.com"));

        Optional<User> actual = users.findByUserId("알린");

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get().getId()).isEqualTo(user.getId()),
                () -> assertThat(actual.get().getUserId()).isEqualTo(user.getUserId())
        );
    }
}
