package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        // given
        User user = new User("user", "1234", "이름", "이메일@gmail.com");

        // when
        User actual = userRepository.save(user);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual).isSameAs(user);
    }

    @Test
    void findByUserId() {
        // given
        User user = userRepository.save(new User("user", "1234", "이름", "이메일@gmail.com"));

        // when
        User actual = userRepository.findByUserId(user.getUserId()).get();

        // then
        assertThat(user).isSameAs(actual);
    }
}
