package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class QuestionRepositoryTest {

    private final QuestionRepository questionRepository;

    public QuestionRepositoryTest(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Test
    void save() {
        Question question = new Question("질문 제목입니다.", "질문 내용입니다.");

        Question savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion.getId()).isNotNull();
    }

    @Test
    void findByDeletedFalse() {
        Question question1 = new Question("1번 질문 제목입니다.", "질문 내용입니다.");
        Question question2 = new Question("2번 질문 제목입니다.", "질문 내용입니다.");
        questionRepository.save(question1);
        questionRepository.save(question2);

        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).contains(question1, question2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        Question question = new Question("1번 질문 제목입니다.", "질문 내용입니다.");
        Question savedQuestion = questionRepository.save(question);

        assertThat(questionRepository.findByIdAndDeletedFalse(savedQuestion.getId())).isPresent();
    }
}
