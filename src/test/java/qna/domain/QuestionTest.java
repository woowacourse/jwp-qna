package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void addQuestion() {
        User user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        User user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        Question question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        answerRepository.save(new Answer(user1, question1, "Answers Contents1"));

        Question question = questionRepository.save(new Question("asd", "qwe"));
        question.writeBy(user2);

        assertThat(user2.getQuestions().size()).isEqualTo(2);
    }

    @Test
    void addAnswer() {
        User user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        User user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        Question question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        Answer answer = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
        Question question = questionRepository.save(new Question("asd", "qwe"));
        question.writeBy(user2);

        question.addAnswer(answer);
        question.addAnswer(answer);

        assertThat(question.getAnswers().size()).isEqualTo(1);
    }

    @Test
    void cannotWrite() {
        User user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        User user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        Question question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        Answer answer = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
        Question question = questionRepository.save(new Question("asd", "qwe"));

        assertThatThrownBy(() -> question.writeBy(null))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void cannotUpdateWriter() {
        User user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        User user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        Question question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        Answer answer = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
        Question question = questionRepository.save(new Question("asd", "qwe"));

        question1.writeBy(user1);

        assertThat(question1.getWriterId()).isEqualTo(user2.getId());
    }
}
