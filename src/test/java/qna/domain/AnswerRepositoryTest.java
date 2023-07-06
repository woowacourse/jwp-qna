package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;


class AnswerRepositoryTest extends RepositoryTest {

    private final AnswerRepository answerRepository;

    AnswerRepositoryTest(final AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Test
    void 질문_id로_답변들을_찾을_수_있다() {
        // given
        final Answer answer = answerRepository.save(AnswerTest.A1);

        // when
        final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(answer.getQuestionId());

        // then
        assertThat(actual).contains(answer);
    }

    @Test
    void id로_답변을_찾을_수_있다() {
        // given
        final Answer expected = answerRepository.save(AnswerTest.A1);

        // when
        final Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }
}
