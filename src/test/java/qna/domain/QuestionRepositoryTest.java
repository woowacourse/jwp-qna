package qna.domain;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class QuestionRepositoryTest extends RepositoryTest {

    private final QuestionRepository questionRepository;

    public QuestionRepositoryTest(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

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
