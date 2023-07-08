package qna.domain;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    EntityManager em;

    private final UserRepository userRepository;

    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void save() {
        User user = new User("gray1234", "password", "gray", "gray@wooteco.com");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    void findByUserId() {
        User user = new User("gray1234", "password", "gray", "gray@wooteco.com");
        User savedUser = userRepository.save(user);

        assertThat(userRepository.findByUserId(savedUser.getUserId())).isPresent();
    }
}
