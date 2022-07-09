package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findByDeletedFalse() {
        Question question = new Question("title", "contents");
        Question question1 = new Question("title2", "contents");
        question.setDeleted(true);
        questionRepository.save(question);
        questionRepository.save(question1);

        List<Question> byDeletedFalse = questionRepository.findByDeletedFalse();

        assertThat(byDeletedFalse.size()).isEqualTo(1);
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
