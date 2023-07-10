package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        // given
        User user = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertAll(
                () -> assertThat(savedUser).isNotNull(),
                () -> assertThat(savedUser.getUserId()).isEqualTo("tjdtls690"),
                () -> assertThat(savedUser.getPassword()).isEqualTo("abc1234@"),
                () -> assertThat(savedUser.getName()).isEqualTo("아벨"),
                () -> assertThat(savedUser.getEmail()).isEqualTo("tjdtls690@gmail.com")
        );
    }

    @Test
    void findAll() {
        // given
        User user1 = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User user2 = new User("aiaiaiai1", "abc1234!", "루쿠", "aiaiaiai1@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        List<User> users = userRepository.findAll();

        // then
        assertAll(
                () -> assertThat(users).hasSize(2),
                () -> assertThat(users.get(0)).usingRecursiveComparison().isEqualTo(user1),
                () -> assertThat(users.get(1)).usingRecursiveComparison().isEqualTo(user2)
        );
    }

    @Test
    void update() {
        // given
        User user = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedUser = userRepository.save(user);
        User loginUser = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User target = new User("tjdtls690", "abc1234@", "루쿠", "aiaiaiai1@gmail.com");

        // when
        savedUser.update(loginUser, target);

        // then
        User updatedUser = userRepository.findByUserId(savedUser.getUserId()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(updatedUser);
    }

    @Test
    void delete() {
        // given
        User user = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        userRepository.save(user);

        // when
        userRepository.delete(user);

        // then
        List<User> findUsers = userRepository.findAll();
        assertThat(findUsers).isEmpty();
    }

}