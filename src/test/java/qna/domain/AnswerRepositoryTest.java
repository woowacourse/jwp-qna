package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.domain.support.QuestionFixture.createQuestion;
import static qna.domain.support.UserFixture.huni;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("질문 id로 삭제되지 않은 응답들을 가져온다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = huni();
        userRepository.save(user);

        Question question = createQuestion(userRepository.findByUserId(user.getUserId()).get().getId());
        questionRepository.save(question);

        answerRepository.save(new Answer(user, question, "contents"));
        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertThat(answers).hasSize(1);
    }

    @DisplayName("응답 id로 삭제되지 않은 응답을 가져온다.")
    @Test
    void findByIdAndDeletedFalse() {
        User user = huni();
        userRepository.save(user);

        Question question = createQuestion(userRepository.findByUserId(user.getUserId()).get().getId());
        questionRepository.save(question);

        Answer answer = answerRepository.save(new Answer(user, question, "contents"));
        Answer foundAnswer = answerRepository.findByIdAndDeletedFalse(answer.getId()).get();

        assertAll(
                () -> assertThat(foundAnswer).isNotNull(),
                () -> assertThat(foundAnswer).isEqualTo(answer)
        );
    }
}
