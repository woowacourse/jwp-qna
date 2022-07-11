package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findByDeletedFalse() {
        Question question1 = questionRepository.save(Q1);
        Question question2 = questionRepository.save(Q2);

        List<Question> foundQuestions = questionRepository.findByDeletedFalse();

        assertThat(foundQuestions).containsExactly(question1, question2);
    }

    @Test
    void findByIdAndDeletedFal1se() {
        Question question = questionRepository.save(Q1);

        Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(question.getId());

        assertThat(foundQuestion).isPresent();
        assertThat(foundQuestion.get()).isEqualTo(question);
    }
}
