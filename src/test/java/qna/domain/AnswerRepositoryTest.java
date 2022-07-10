package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;
import static qna.domain.AnswerTest.A2;
import static qna.domain.AnswerTest.A3;
import static qna.domain.QuestionTest.Q1;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


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

        Answer actual = answer.get();
        assertThat(actual).isEqualTo(A1);
    }
}
