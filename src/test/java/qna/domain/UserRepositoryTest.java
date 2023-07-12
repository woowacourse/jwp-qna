package qna.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.JpaConfig;

import java.util.Optional;

@DataJpaTest
@Import(JpaConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void User를_저장하고_조회한다() {
        User user = UserTest.JAVAJIGI;

        User save = userRepository.save(user);
        User saved = userRepository.findById(user.getId()).get();

        Assertions.assertThat(save).isSameAs(saved);
    }
}
