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
public class QuestionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findByDeletedFalse() {
        User user1 = userRepository.save(JAVAJIGI);
        User user2 = userRepository.save(SANJIGI);

        Question Q1 = new Question("title1", "contents1").writeBy(user1);
        Question Q2 = new Question("title1", "contents1").writeBy(user2);
        Question question1 = questionRepository.save(Q1);
        Question question2 = questionRepository.save(Q2);

        List<Question> foundQuestions = questionRepository.findByDeletedFalse();

        assertThat(foundQuestions).containsExactly(question1, question2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);

        Question Q1 = new Question("title1", "contents1").writeBy(user);
        Question question = questionRepository.save(Q1);

        Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(question.getId());

        assertThat(foundQuestion).hasValue(question);
    }
}
