package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
public class QuestionRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void save_메서드로_Q1을_저장한다() {
        // given
        final User javajigi = new User(
                "javajigi",
                "password",
                "name",
                "javajigi@slipp.net"
        );

        userRepository.save(javajigi);

        final Question q1 = new Question("title1", "contents1").writeBy(javajigi);

        // when
        final Question actual = questionRepository.save(q1);

        // then
        assertThat(actual.getId()).isPositive();
    }

    @Test
    void findById_메서드로_Q1을_조회한다() {
        // given
        final User javajigi = new User(
                "javajigi",
                "password",
                "name",
                "javajigi@slipp.net"
        );

        userRepository.save(javajigi);

        final Question q1 = new Question("title1", "contents1").writeBy(javajigi);

        questionRepository.save(q1);

        // when
        final Optional<Question> actual = questionRepository.findById(q1.getId());

        // then
        assertThat(actual).isPresent();
    }
}
