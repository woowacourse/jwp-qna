package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class AnswerTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void save() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");

        answerRepository.save(answer);

        assertThat(answer.getId()).isNotNull();
    }

    @Test
    void updateContent() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        answer.setContents("asihjdiaosshdjioshjod");

        assertThat(question.getAnswers().get(0).getContents()).isEqualTo(answer.getContents());
    }

    @Test
    void checkUser() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        assertThat(answer.getWriterId()).isEqualTo(user.getId());
    }

    @Test
    void cannotChangeUser() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        User newbie = userRepository.save(new User(2L, "newbie", "password", "newbie", "newbie@slipp.net"));

        answer.toWriter(newbie);
        assertThat(answer.getWriterId()).isEqualTo(user.getId());
    }

    @Test
    void cannotChangeQuestion() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        Question question2 = questionRepository.save(new Question("asd", "qwe"));
        answer.toQuestion(question2);
        assertThat(answer.getQuestionId()).isEqualTo(question.getId());
    }

    @Test
    void checkCreateTime() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        assertThat(answer.getCreateAt()).isNotNull();
    }

    @Test
    void checkUpdateTime() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        answer.setContents("zxc");

        assertThat(answer.getUpdatedAt()).isNotNull();
    }

    @Test
    void deleteAnswer() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        answerRepository.delete(answer);

        em.flush();
        em.clear();

        Question question1 = questionRepository.findById(question.getId()).get();

        assertThat(question1.getAnswers()).isEmpty();
    }

    @Test
    void checkOwner() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer(user, question, "Answers Contents1");
        answerRepository.save(answer);

        assertThat(answer.isOwner(user)).isTrue();
    }

    @Test
    void updateQuestion() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Question question = questionRepository.save(new Question("title2", "contents2").writeBy(user));
        Answer answer = new Answer();
        answerRepository.save(answer);
        answer.toQuestion(question);

        assertAll(
                () -> assertThat(answer.getQuestionId()).isEqualTo(question.getId()),
                () -> assertThat(answer.getWriter()).isNull()
        );
    }

    @Test
    void updateWriter() {
        User user = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        Answer answer = new Answer();
        answerRepository.save(answer);
        answer.toWriter(user);

        assertAll(
                () -> assertThat(answer.getQuestion()).isNull(),
                () -> assertThat(answer.getWriterId()).isEqualTo(user.getId())
        );
    }
}
