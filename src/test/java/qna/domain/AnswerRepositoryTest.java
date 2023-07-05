package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;

    AnswerRepositoryTest(final AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Test
    void 질문_id로_답변들을_찾을_수_있다() {
        // given
        final Answer answer = answerRepository.save(AnswerTest.A1);
        final Long questionId = answer.getQuestionId();

        // when
        final List<Answer> result = answerRepository.findByQuestionIdAndDeletedFalse(questionId);

        // then
        assertThat(result).contains(answer);
    }

    @Test
    void id로_답변을_찾을_수_있다() {
        // given
        final Answer answer = answerRepository.save(AnswerTest.A1);

        // when
        final Optional<Answer> result = answerRepository.findByIdAndDeletedFalse(answer.getId());

        // then
        assertAll(
                () -> assertThat(result).isPresent(),
                () -> assertThat(result.get()).isEqualTo(answer)
        );
    }
}
