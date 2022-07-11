package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("userId로 user를 조회하면 같은 엔티티이다.")
    @Test
    void findByUserId() {
        User user = userRepository.save(new User("1324356456", "passsword", "giron", "example@xxx.com"));
        User findUser = userRepository.findByUserId(user.getUserId()).get();

        assertThat(user).isSameAs(findUser);
    }

    @DisplayName("없는 userId로 user를 조회하면 empty()를 반환한다.")
    @Test
    void findByUserIdWhenFailure() {
        String noUserId = "-123";

        assertThat(userRepository.findByUserId(noUserId)).isEmpty();
    }
}
