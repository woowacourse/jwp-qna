package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;
import static qna.domain.AnswerTest.A2;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class AnswerRepositoryTest {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    public AnswerRepositoryTest(QuestionRepository questionRepository, AnswerRepository answerRepository,
                                UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    @DisplayName("questionId와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        userRepository.save(JAVAJIGI);
        userRepository.save(SANJIGI);
        Question question = questionRepository.save(Q1);
        System.out.println("question.getId() = " + question.getId());
        System.out.println("Q1.getId() = " + Q1.getId());

        answerRepository.save(A1);
        answerRepository.save(A2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());
        assertThat(answers).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        userRepository.save(JAVAJIGI);
        questionRepository.save(Q1);
        Answer answer = answerRepository.save(A1);

        Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(answer.getId());
        assertThat(actual).hasValue(answer);
    }
}
