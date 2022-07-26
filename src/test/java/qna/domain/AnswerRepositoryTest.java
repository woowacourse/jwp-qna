package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

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
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user1 = userRepository.save(JAVAJIGI);
        User user2 = userRepository.save(SANJIGI);

        Question Q1 = new Question("title1", "contents1").writeBy(user1);
        Question question = questionRepository.save(Q1);

        Answer A1 = new Answer(user1, Q1, "Answers Contents1");
        Answer A2 = new Answer(user2, Q1, "Answers Contents2");
        Answer answer1 = answerRepository.save(A1);
        Answer answer2 = answerRepository.save(A2);

        List<Answer> foundAnswers = answerRepository.findByQuestionIdAndDeletedFalse(
                question.getId());

        assertThat(foundAnswers).containsExactly(answer1, answer2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);

        Question Q1 = new Question("title1", "contents1").writeBy(user);
        questionRepository.save(Q1);

        Answer A1 = new Answer(user, Q1, "Answers Contents1");
        Answer answer = answerRepository.save(A1);

        Optional<Answer> foundAnswers = answerRepository.findByIdAndDeletedFalse(answer.getId());

        assertThat(foundAnswers).hasValue(answer);
    }
}
