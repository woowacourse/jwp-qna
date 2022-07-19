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
class AnswerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("deleted가 false인 답변들을 질문 Id로 조회한다.")
    void findByQuestionIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1").writeBy(user);
        Question question = questionRepository.save(Q1);
        List<Answer> answers = answerRepository.saveAll(Arrays.asList(
                new Answer(user, question, "content1"),
                new Answer(user, question, "content2")
        ));

        List<Answer> findAnswer = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertThat(findAnswer).containsExactlyElementsOf(answers);
    }

    @Test
    @DisplayName("deleted가 false인 답변을 Id로 조회한다.")
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1").writeBy(user);
        Question question = questionRepository.save(Q1);
        Answer answer = answerRepository.save(new Answer(user, question, "content"));

        Optional<Answer> findAnswer = answerRepository.findByIdAndDeletedFalse(answer.getId());

        assertThat(findAnswer).isPresent();
        assertThat(findAnswer.get()).isEqualTo(answer);
    }
}
