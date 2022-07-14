package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.JpaAuditingConfig;

@Import(JpaAuditingConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    public AnswerRepositoryTest(AnswerRepository answerRepository, UserRepository userRepository,
                                QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = userRepository.save(new User("aaa", "1234", "jurl", "dbswnfl2"));
        Question question = questionRepository.save(new Question("title", "content"));
        Answer answer = new Answer(user, question, "content");
        Answer save = answerRepository.save(answer);

        List<Answer> byQuestionIdAndDeletedFalse = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertAll(
                () -> assertThat(byQuestionIdAndDeletedFalse.size()).isEqualTo(1),
                () -> assertThat(byQuestionIdAndDeletedFalse.get(0)).isEqualTo(save)
        );
    }

    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(new User("aaa", "1234", "jurl", "dbswnfl2"));
        Question question = questionRepository.save(new Question("title", "content"));
        Answer answer = new Answer(user, question, "content");
        Answer save = answerRepository.save(answer);

        Optional<Answer> byIdAndDeletedFalse = answerRepository.findByIdAndDeletedFalse(save.getId());

        assertThat(byIdAndDeletedFalse).hasValue(answer);
    }
}
