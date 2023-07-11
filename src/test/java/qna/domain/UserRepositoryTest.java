package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.UserFixture.JAVAJIGI;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.configuration.JpaConfiguration;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Import(JpaConfiguration.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void save_메서드로_JAVAJIGI을_저장한다() {
        // when
        final User actual = userRepository.save(JAVAJIGI);

        // then
        assertThat(actual.getId()).isPositive();
    }

    @Test
    void findById_메서드로_JAVAJIGI을_조회한다() {
        // given
        final User javajigi = userRepository.save(JAVAJIGI);

        // when
        final Optional<User> actual = userRepository.findById(javajigi.getId());

        // then
        assertThat(actual).isPresent();
    }
}
