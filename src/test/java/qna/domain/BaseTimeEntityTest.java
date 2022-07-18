package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BaseTimeEntityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void auditingTest() {
        final String userId = "yxxnghwan";
        final String password = "password";
        final String name = "알렉스";
        final String email = "younghwan960@gmail.com";
        final User user = new User(userId, password, name, email);

        userRepository.save(user);

        assertAll(
                () -> assertThat(user.getCreatedAt()).isNotNull(),
                () -> assertThat(user.getUpdatedAt()).isNotNull()
        );
    }
}
