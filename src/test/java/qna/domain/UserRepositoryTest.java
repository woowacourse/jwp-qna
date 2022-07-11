package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("userId로 해당 유저를 찾는다.")
    @Test
    void findByUserId() {
        final User user = UserFixtures.seungpang();
        userRepository.save(user);
        final User findUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow();

        assertAll(() -> {
            assertThat(findUser.getId()).isNotNull();
            assertThat(findUser).isEqualTo(user);
        });
    }
}
