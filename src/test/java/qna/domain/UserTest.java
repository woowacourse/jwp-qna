package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    public static final User JAVAJIGI = new User("javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User("sanjigi", "password", "name", "sanjigi@slipp.net");


    @Test
    void saveUser() {
        User user = userRepository.save(JAVAJIGI);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void findUser() {
        User user = userRepository.save(JAVAJIGI);
        User user2 = userRepository.save(SANJIGI);
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(2);
        assertThat(users).contains(user, user2);
    }

    @Test
    void findAnswerById() {
        User user = userRepository.save(JAVAJIGI);
        User findUser = userRepository.findById(user.getId()).get();

        assertThat(user).isEqualTo(findUser);
    }
}
