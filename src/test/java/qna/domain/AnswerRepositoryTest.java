package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;
import static qna.domain.AnswerTest.A2;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerRepositoryTest(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @BeforeEach
    void setUp() {
        questionRepository.save(QuestionTest.Q1);
        questionRepository.save(QuestionTest.Q2);
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        answerRepository.save(A1);
        answerRepository.save(A2);

        final List<Answer> 찾은거 = answerRepository.findByQuestionIdAndDeletedFalse(A1.getQuestionId());
        assertThat(찾은거).hasSize(2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        answerRepository.save(A1);
        assertThat(
                answerRepository.findByIdAndDeletedFalse(A1.getId())
        ).isPresent();

        A1.setDeleted(true);

        assertThat(
                answerRepository.findByIdAndDeletedFalse(A1.getId())
        ).isEmpty();
    }
}