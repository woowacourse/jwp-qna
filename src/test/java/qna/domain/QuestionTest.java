package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    public static final Question Q1 = new Question(null, "title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question(null, "title2", "contents2").writeBy(UserTest.SANJIGI);

    private User user;

    @BeforeEach
    void before() {
        user = userRepository.save(new User("user1", "password", "name", "user@user"));
    }

    @Test
    void saveQuestion() {
        Question question = new Question("title", "contents").writeBy(user);

        Question savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion.getId()).isNotNull();
    }

    @Test
    void findQuestion() {
        Question question1 = new Question("title", "contents").writeBy(user);
        Question question2 = new Question("title", "contents").writeBy(user);

        Question savedQuestion1 = questionRepository.save(question1);
        Question savedQuestion2 = questionRepository.save(question2);

        List<Question> answers = questionRepository.findAll();

        assertThat(answers).hasSize(2);
        assertThat(answers).contains(savedQuestion1, savedQuestion2);
    }

    @Test
    void findQuestionById() {
        Question question = new Question("title", "contents").writeBy(user);
        Question savedQuestion = questionRepository.save(question);

        Question findQuestion = questionRepository.findById(savedQuestion.getId()).get();

        assertThat(findQuestion).isEqualTo(savedQuestion);
    }

    @Test
    void deleteQuestion() {
        Question question = new Question("title", "contents").writeBy(user);
        questionRepository.save(question);

        userRepository.delete(user);

        assertThatThrownBy(() -> userRepository.flush()).isInstanceOf(DataIntegrityViolationException.class);
    }
}
