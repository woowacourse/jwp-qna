package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    public static final Answer A1 = new Answer(UserRepositoryTest.JAVAJIGI, QuestionRepositoryTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserRepositoryTest.SANJIGI, QuestionRepositoryTest.Q1, "Answers Contents2");

    @Test
    void save_메서드로_A1을_저장한다() {
        // when
        final Answer actual = answerRepository.save(A1);

        // then
        assertThat(actual.getId()).isPositive();
    }

    @Test
    void findById_메서드로_A1을_조회한다() {
        // given
        final Answer a1 = answerRepository.save(A1);

        // when
        final Optional<Answer> actual = answerRepository.findById(a1.getId());

        // then
        assertThat(actual).isPresent();
    }
}
