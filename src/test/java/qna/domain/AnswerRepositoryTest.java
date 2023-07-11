package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("답변을 저장한다.")
    void save() {
        // given
        // when
        final Answer savedAnswer = answerRepository.save(AnswerTest.A1);

        // then
        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    @DisplayName("질문과 삭제 여부로 답변을 조회한다.")
    void findByQuestionIdAndDeletedFalse() {
        // given
        final Answer savedAnswer = answerRepository.save(AnswerTest.A1);

        // when
        final List<Answer> foundResult =
            answerRepository.findByQuestionIdAndDeletedFalse(savedAnswer.getQuestionId());

        // then
        assertThat(foundResult).usingRecursiveComparison()
            .isEqualTo(Collections.singletonList(savedAnswer));
    }


    @Test
    @DisplayName("답변의 id와 삭제 여부로 답변을 조회한다.")
    void findByIdAndDeletedFalse() {
        // given
        final Answer savedAnswer = answerRepository.save(AnswerTest.A1);

        // when
        final Optional<Answer> foundAnswer =
            answerRepository.findByIdAndDeletedFalse(savedAnswer.getId());

        // then
        assertThat(foundAnswer).isPresent()
            .get()
            .isEqualTo(savedAnswer);
    }
}
