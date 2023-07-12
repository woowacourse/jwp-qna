package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@DataJpaTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;

    public AnswerRepositoryTest(final AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer = new Answer(1L, 1L, "answer");
        answerRepository.save(answer);
        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(1L);

        assertThat(answers).isNotEmpty();
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer answer = new Answer(1L, 1L, "answer");
        Answer savedAnswer = answerRepository.save(answer);
        Answer findAnswer = answerRepository.findByIdAndDeletedFalse(savedAnswer.getId()).get();

        assertThat(findAnswer).isSameAs(answer);
    }
}
