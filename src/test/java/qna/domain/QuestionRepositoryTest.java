package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class QuestionRepositoryTest {

    private final QuestionRepository questionRepository;

    public QuestionRepositoryTest(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Test
    void findByDeletedFalse() {
        Question question = new Question("title", "contents");
        Question question1 = new Question("title2", "contents");
        question.setDeleted(true);
        questionRepository.save(question);
        questionRepository.save(question1);

        List<Question> byDeletedFalse = questionRepository.findByDeletedFalse();

        assertAll(
                () -> assertThat(byDeletedFalse.size()).isEqualTo(1),
                () -> assertThat(byDeletedFalse.get(0)).isEqualTo(question1));
    }

    @Test
    void findByIdAndDeletedFalse() {
        Question question = new Question("title", "contents");
        question.setDeleted(true);
        questionRepository.save(question);

        List<Question> byDeletedFalse = questionRepository.findByDeletedFalse();

        assertThat(byDeletedFalse.size()).isEqualTo(0);
    }
}
