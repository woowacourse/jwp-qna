package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId() {
        // given
        final User user = new User("test", "test123", "test", "test@test.com");
        final User savedUser = userRepository.save(user);

        // when
        final User actual = userRepository.findByUserId(savedUser.getUserId()).get();

        // then
        assertThat(actual).isEqualTo(savedUser);
    }
}
