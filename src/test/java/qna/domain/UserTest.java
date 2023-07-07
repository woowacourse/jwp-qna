package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.UserFixture.JAVAJIGI;
import static qna.fixture.UserFixture.SANJIGI;

@DataJpaTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자를_저장한다() {
        // given, when
        User savedUser = userRepository.save(JAVAJIGI);

        // then
        assertThat(savedUser.getUserId()).isNotNull();
    }

    @Test
    void 사용자를_찾는다() {
        // given, when
        User savedUser1 = userRepository.save(JAVAJIGI);
        User savedUser2 = userRepository.save(SANJIGI);

        List<User> users = userRepository.findAll();

        // then
        assertThat(users).containsExactlyInAnyOrder(savedUser1, savedUser2);
    }

    @Test
    void id로_사용자를_찾는다() {
        // given, when
        User savedUser1 = userRepository.save(JAVAJIGI);
        User savedUser2 = userRepository.save(SANJIGI);

        User findUser = userRepository.findById(savedUser1.getId()).get();

        // then
        assertThat(findUser).isEqualTo(savedUser1);
    }
}
