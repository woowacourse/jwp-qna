package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void changeName() {
        User user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        User user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        Question question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        Answer answer1 = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));

        user1.update(new User("user1", "password", "name", "user1@slipp.net"),
                new User("user1", "password", "alpha", "alpha1@slipp.net"));

        assertThat(answer1.getWriter().getName()).isEqualTo("alpha");
    }
}
