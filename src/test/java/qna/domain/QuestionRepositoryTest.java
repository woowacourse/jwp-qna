package qna.domain;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
        Question question = new Question("제목", "내용");
        questionRepository.save(question);
        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).isNotEmpty();
    }

    @Test
    void findByIdAndDeletedFalse() {
        Question question = new Question("제목", "내용");
        Question savedQuestion = questionRepository.save(question);
        Question findQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId()).get();

        assertThat(findQuestion).isSameAs(question);
    }
}
