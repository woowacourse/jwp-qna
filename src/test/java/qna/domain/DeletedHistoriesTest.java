package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DeletedHistoriesTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void deleteQuestion() {
        DeleteHistories deleteHistories = new DeleteHistories(new ArrayList<>());
        User user = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com")
        );
        Question question = questionRepository.save(
                new Question("asd", "asd")
        );
        question.writeBy(user);

        deleteHistories.deleteQuestion(question);

        assertThat(deleteHistories.getDeleteHistories()).isEqualTo(
                List.of(new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter())));
    }

    @Test
    void deleteQuestionAlreadyDeleted() {
        DeleteHistories deleteHistories = new DeleteHistories(new ArrayList<>());
        User user = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com")
        );
        Question question = questionRepository.save(
                new Question("asd", "asd")
        );
        question.writeBy(user);
        question.setDeleted(true);

        deleteHistories.deleteQuestion(question);

        assertThat(deleteHistories.getDeleteHistories()).isEmpty();
    }

    @Test
    void deleteAnswers() {
        DeleteHistories deleteHistories = new DeleteHistories(new ArrayList<>());
        User user = userRepository.save(
                new User("alpha", "password", "alpha", "bcc0830@naver.com")
        );
        Question question = questionRepository.save(
                new Question("asd", "asd")
        );
        question.writeBy(user);
        Answer answer1 = answerRepository.save(new Answer(user, question, "asd"));
        Answer answer2 = answerRepository.save(new Answer(user, question, "asd"));
        Answer answer3 = answerRepository.save(new Answer(user, question, "asd"));
        answer3.setDeleted(true);

        Answers answers = new Answers(
                List.of(answer1, answer2, answer3));

        deleteHistories.deleteAnswers(answers);

        assertThat(deleteHistories.getDeleteHistories()).isEqualTo(
                List.of(new DeleteHistory(ContentType.ANSWER, answer1.getId(), answer1.getWriter()),
                        new DeleteHistory(ContentType.ANSWER, answer2.getId(), answer2.getWriter())));
    }
}
