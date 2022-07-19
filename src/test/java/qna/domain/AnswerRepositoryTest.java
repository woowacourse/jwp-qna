package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    @DisplayName("questionId와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Q1.writeBy(user);
        Question question = questionRepository.save(Q1);
        Answer answer1 = new Answer(user, question, "answer1");
        Answer answer2 = new Answer(user, question, "answer2");
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());
        assertThat(answers).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 답들을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Q1.writeBy(user);
        Question question = questionRepository.save(Q1);
        Answer answer1 = new Answer(user, question, "answer1");
        Answer answer = answerRepository.save(answer1);

        Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(answer.getId());
        assertThat(actual).hasValue(answer);
    }
}
