package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.CannotDeleteException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        assertThatThrownBy(() -> answers.delete(user1))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    void delete() throws CannotDeleteException {
        User user1 = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com"));
        Question question = questionRepository.save(
                new Question("why learning programming is so damn hard?", "same as title"));
        question.writeBy(user1);
        Answer answer1 = answerRepository.save(
                new Answer(user1, question, "content"));
        Answer answer2 = answerRepository.save(
                new Answer(user1, question, "content"));
        Answers answers = new Answers(List.of(answer1, answer2));

        List<DeleteHistory> histories = answers.delete(user1);

        assertAll(
                () -> assertThat(answers.getAnswers()).isEmpty(),
                () -> assertThat(histories).isEqualTo(List.of(
                        new DeleteHistory(ContentType.ANSWER, answer1.getId(), user1),
                        new DeleteHistory(ContentType.ANSWER, answer2.getId(), user1)
                ))
        );
    }

    @Test
    void add() {
        User user1 = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com"));
        Question question = questionRepository.save(
                new Question("why learning programming is so damn hard?", "same as title"));
        question.writeBy(user1);
        Answers answers = new Answers();

        answers.addAnswer(answerRepository.save(
                new Answer(user1, question, "content")));

        assertThat(answers.getAnswers().size()).isEqualTo(1);
    }
}
