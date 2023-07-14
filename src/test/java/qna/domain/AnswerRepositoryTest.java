package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private AnswerTest answerTest;
    private QuestionTest questionTest;

    public AnswerRepositoryTest(final AnswerRepository answerRepository, final QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.questionTest = new QuestionTest();
        this.answerTest = new AnswerTest(questionTest);
    }

    @BeforeEach
    void setUp() {
        questionRepository.save(questionTest.Q1);
        questionRepository.save(questionTest.Q2);
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        answerRepository.save(answerTest.makeAnswer1());
        answerRepository.save(answerTest.makeAnswer2());

        final List<Answer> 찾은거 = answerRepository.findByQuestionIdAndDeletedFalse(answerTest.makeAnswer1().getQuestion().getId());
        assertThat(찾은거).hasSize(2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        final Answer answer = answerTest.makeAnswer1();
        answerRepository.save(answer);
        assertThat(
                answerRepository.findByIdAndDeletedFalse(answer.getId())
        ).isPresent();

        answer.setDeleted(true);

        assertThat(
                answerRepository.findByIdAndDeletedFalse(answer.getId())
        ).isEmpty();
    }
}
