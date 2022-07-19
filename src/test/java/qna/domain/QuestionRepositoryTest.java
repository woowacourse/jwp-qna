package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.JpaAuditingConfig;

@Import(JpaAuditingConfig.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class QuestionRepositoryTest {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QuestionRepositoryTest(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Test
    void findByDeletedFalse() {
        Question deletedQuestion = new Question("title", "contents");
        deletedQuestion.setDeleted(true);
        questionRepository.save(deletedQuestion);
        Question question = new Question("title2", "contents");
        questionRepository.save(question);

        List<Question> actual = questionRepository.findByDeletedFalse();

        assertAll(
                () -> assertThat(actual.size()).isEqualTo(1),
                () -> assertThat(actual.get(0)).isEqualTo(question));
    }

    @Test
    void findByIdAndDeletedFalse() {
        Question deletedQuestion = new Question("title", "contents");
        deletedQuestion.setDeleted(true);
        questionRepository.save(deletedQuestion);

        List<Question> actual = questionRepository.findByDeletedFalse();

        assertThat(actual.size()).isEqualTo(0);
    }
}
