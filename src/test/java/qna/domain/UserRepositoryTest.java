package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("userId로 사용자 조회 - 존재 O")
    void findByUserId() {
        final User user = userRepository.save(UserTest.JAVAJIGI);

        assertThat(userRepository.findByUserId(user.getUserId())).contains(user);
    }

    @Test
    @DisplayName("userId로 사용자 조회 - 존재 X")
    void findByUserIdIsNotExist() {
        userRepository.save(UserTest.JAVAJIGI);

        assertThat(userRepository.findByUserId("dummy")).isEmpty();
    }
}
