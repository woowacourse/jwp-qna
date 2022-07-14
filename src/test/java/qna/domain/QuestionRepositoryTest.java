package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaAuditingConfig.class)
class QuestionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("deleted가 false인 질문들을 조회한다.")
    void findByDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1").writeBy(user);
        Question Q2 = new Question("title2", "contents2").writeBy(user);
        List<Question> questions = questionRepository.saveAll(Arrays.asList(Q1, Q2));

        List<Question> findQuestions = questionRepository.findByDeletedFalse();

        assertThat(findQuestions).containsExactlyElementsOf(questions);
    }

    @Test
    @DisplayName("deleted가 false인 질문을 Id로 조회한다.")
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1").writeBy(user);
        Question question = questionRepository.save(Q1);

        Optional<Question> findQuestion = questionRepository.findByIdAndDeletedFalse(question.getId());

        assertThat(findQuestion).isPresent();
        assertThat(findQuestion.get()).isEqualTo(question);
    }
}
