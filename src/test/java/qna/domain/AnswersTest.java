package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.CannotDeleteException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class AnswersTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void validateOther() {
        User user1 = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com"));
        User user2 = userRepository.save(
                new User("tomi", "password", "tomi", "tomi1234@gmail.com"));
        Question question = questionRepository.save(
                new Question("why learning programming is so damn hard?", "same as title"));
        question.writeBy(user1);
        Answers answers = new Answers(List.of(
                answerRepository.save(
                        new Answer(user1, question, "content")),
                answerRepository.save(
                        new Answer(user2, question, "content"))
        ));

        assertThatThrownBy(() -> answers.validateOther(user1))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
