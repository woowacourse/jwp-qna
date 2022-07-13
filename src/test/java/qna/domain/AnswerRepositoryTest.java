package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;
import static qna.domain.AnswerTest.A2;
import static qna.domain.QuestionTest.Q1;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    @DisplayName("questionId와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        questionRepository.save(Q1);
        answerRepository.save(A1);
        answerRepository.save(A2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(Q1.getId());
        assertThat(answers).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        questionRepository.save(Q1);
        answerRepository.save(A1);

        Optional<Answer> answer = answerRepository.findByIdAndDeletedFalse(A1.getId());
        assertThat(answer).isPresent();

        assertThat(answer).hasValue(A1);
    }
}
