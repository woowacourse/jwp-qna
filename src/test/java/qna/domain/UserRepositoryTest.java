package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저를 저장한다.")
    void save() {
        final String userId = "yxxnghwan";
        final String password = "password";
        final String name = "알렉스";
        final String email = "younghwan960@gmail.com";
        final User user = new User(userId, password, name, email);

        final User saved = userRepository.save(user);

        assertThat(saved).extracting("userId", "password", "name", "email")
                .containsExactly(userId, password, name, email);
    }
}