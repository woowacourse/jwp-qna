package qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;

    public AnswerRepositoryTest(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Test
    void save() {
        Answer answer = new Answer(1L, 1L, "질문에 대한 답변입니다.");

        Answer savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer answer = new Answer(1L, 1L, "1번 질문에 대한 답변입니다.");
        Answer savedAnswer = answerRepository.save(answer);

        assertThat(answerRepository.findByIdAndDeletedFalse(savedAnswer.getId())).isPresent();
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer1 = new Answer(1L, 1L, "1번 질문에 대한 답변입니다.");
        Answer answer2 = new Answer(1L, 2L, "2번 질문에 대한 답변입니다.");
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(1L);

        assertThat(answers).contains(answer1);
    }
}
