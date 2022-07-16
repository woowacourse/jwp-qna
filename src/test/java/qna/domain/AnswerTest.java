package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {
    
    private User user1;
    private User user2;
    
    private Question question1;

    private Answer answer1;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    private void setUp() {
        user1 = userRepository.save(new User(1L, "user1", "password", "name", "user1@slipp.net"));
        user2 = userRepository.save(new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net"));
        question1 = questionRepository.save(new Question("title2", "contents2").writeBy(user2));
        answer1 = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
    }

    @Test
    void save() {
        Answer answer = answerRepository.save(answer1);
        assertThat(answer.getId()).isEqualTo(answer1.getId());
    }

    @Test
    void updateContent() {
        answer1.setContents("asihjdiaosshdjioshjod");

        assertThat(question1.getAnswers().get(0).getContents()).isEqualTo(answer1.getContents());
    }

    @Test
    void checkUser() {
        Answer answer = answerRepository.findById(answer1.getId()).get();
        assertThat(answer.getWriterId()).isEqualTo(user1.getId());
    }

    @Test
    void cannotChangeUser() {
        Answer answer = answerRepository.findById(answer1.getId()).get();
        answer.toWriter(user2);
        assertThat(answer.getWriterId()).isEqualTo(user1.getId());
    }

    @Test
    void cannotChangeQuestion() {
        Answer answer = answerRepository.findById(answer1.getId()).get();
        Question question2 = questionRepository.save(new Question("asd", "qwe"));
        answer.toQuestion(question2);
        assertThat(answer.getQuestionId()).isEqualTo(question1.getId());
    }

    @Test
    void checkCreateTime() {
        Answer answer = answerRepository.save(new Answer(user2, question1, "qwe"));

        assertThat(answer.getCreateAt()).isNotNull();
    }

    @Test
    void checkUpdateTime() {
        Answer answer = answerRepository.save(new Answer(user2, question1, "qwe"));
        answer.setContents("zxc");

        assertThat(answer.getUpdatedAt()).isNotNull();
    }
}
