package qna.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {
    
    private User user1;
    private User user2;
    
    private Question question1;
    private Question question2;
    
    private Answer answer1;
    private Answer answer2;
    
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
        question2 = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        answer1 = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
        answer2 = answerRepository.save(new Answer(user2, question1, "Answers Contents2"));
    }

    @Test
    void save() {
        Answer answer = answerRepository.save(answer1);
        assertThat(answer.getId()).isEqualTo(answer1.getId());
    }

    @Test
    void updateQuestion() {
        answer1.toQuestion(question2);
        assertThat(answer1.getQuestionId()).isEqualTo(question2.getId());
        assertThat(question1.getAnswers().size()).isEqualTo(1);
        assertThat(question2.getAnswers().size()).isEqualTo(1);
    }

    @PersistenceContext
    EntityManager em;
    @Test
    void deleteAnswer() {
        answerRepository.delete(answer1);

        em.flush();
        em.clear();

        Question q = questionRepository.findById(question1.getId()).get();

        assertThat(q.getAnswers().size()).isEqualTo(1);
    }

    @Test
    void updateContent() {

        System.out.println("#####");
        System.out.println("#####");
        answer1.setContents("asihjdiaosshdjioshjod");

        assertThat(question1.getAnswers().get(0).getContents()).isEqualTo(answer1.getContents());
    }
}
