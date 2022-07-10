package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.domain.support.UserFixture.huni;

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

    @DisplayName("userId로 사용자를 찾는다.")
    @Test
    void findByUserId() {
        User user = userRepository.save(huni());
        User foundUser = userRepository.findByUserId(user.getUserId()).get();

        assertAll(
                () -> assertThat(foundUser).isNotNull(),
                () -> assertThat(foundUser).isEqualTo(user)
        );
    }
}
