package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class QuestionRepositoryTest extends RepositoryTest {

    private final QuestionRepository questionRepository;

    QuestionRepositoryTest(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Test
    void 삭제되지_않은_질문들을_찾을_수_있다() {
        // given
        questionRepository.save(QuestionTest.Q1);
        questionRepository.save(QuestionTest.Q2);

        // when
        final List<Question> actual = questionRepository.findByDeletedFalse();

        // then
        assertThat(actual).hasSize(2);
    }

    @Test
    void id로_질문을_찾을_수_있다() {
        // given
        final Question expected = questionRepository.save(QuestionTest.Q2);

        // when
        final Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        Assertions.assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }
}
