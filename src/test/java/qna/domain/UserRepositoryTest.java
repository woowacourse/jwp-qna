package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저를_저장한다() {
        // when
        User actual = userRepository.save(JAVAJIGI);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 유저를_조회한다() {
        // given
        User actual = userRepository.save(JAVAJIGI);

        // when
        User expected = userRepository.findByUserId(actual.getUserId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
