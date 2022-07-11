package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User savedUser = userRepository.save(JAVAJIGI);

        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(JAVAJIGI);
    }

    @Test
    void findById() {
        User savedUser = userRepository.save(JAVAJIGI);

        Optional<User> result = userRepository.findById(savedUser.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedUser);
    }

    @Test
    void findByUserId() {
        User savedUser = userRepository.save(JAVAJIGI);

        Optional<User> result = userRepository.findByUserId(savedUser.getUserId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedUser);
    }

    @Test
    void findAll() {
        User savedUser1 = userRepository.save(JAVAJIGI);
        User savedUser2 = userRepository.save(SANJIGI);

        List<User> result = userRepository.findAll();

        assertThat(result).contains(savedUser1, savedUser2);
    }

    @Test
    void deleteById() {
        User savedUser = userRepository.save(JAVAJIGI);

        userRepository.deleteById(savedUser.getId());
        Optional<User> result = userRepository.findById(savedUser.getId());

        assertThat(result).isEmpty();
    }

}
