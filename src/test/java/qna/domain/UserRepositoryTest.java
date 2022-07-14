package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = new User("rennon", "1234", "rennon", "rennon@woowa.com");
        User foundUser = userRepository.save(user);

        assertThat(user).isEqualTo(foundUser);
    }

    @Test
    void findByUserId() {
        User user = userRepository.save(JAVAJIGI);

        Optional<User> foundUser = userRepository.findByUserId(user.getUserId());

        assertThat(foundUser).hasValue(user);
    }
}
