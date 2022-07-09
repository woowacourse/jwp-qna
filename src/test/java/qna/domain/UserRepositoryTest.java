package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User user = new User("jurl", "1234", "주디", "dbswnfl2@");
        User save = userRepository.save(user);

        assertThat(user == save).isTrue();
    }

    @Test
    void findByUserId() {
        User user = new User("jurl", "1234", "주디", "dbswnfl2@");
        userRepository.save(user);

        userRepository.findByUserId(user.getUserId()).ifPresent(
                it -> assertThat(it).isEqualTo(user)
        );
    }
}
