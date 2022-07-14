package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;
import static qna.domain.AnswerTest.A2;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer = answerRepository.save(A1);

        List<Answer> foundAnswers = answerRepository.findByQuestionIdAndDeletedFalse(
                answer.getQuestionId());

        assertThat(foundAnswers).containsExactly(answer);
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer answer = answerRepository.save(A2);

        Optional<Answer> foundAnswers = answerRepository.findByIdAndDeletedFalse(answer.getId());

        assertThat(foundAnswers).hasValue(answer);
    }
}
