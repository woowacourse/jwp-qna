package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {
    @Autowired
    private AnswerRepository answerRepository;
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void saveAnswer() {
        Answer savedAnswer = answerRepository.save(A1);

        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    void findAnswer() {
        Answer savedAnswer1 = answerRepository.save(A1);
        Answer savedAnswer2 = answerRepository.save(A2);
        List<Answer> answers = answerRepository.findAll();

        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers).contains(savedAnswer1, savedAnswer2);
    }

    @Test
    void findAnswerById() {
        Answer savedAnswer = answerRepository.save(A1);
        Answer findAnswer = answerRepository.findById(savedAnswer.getId()).get();

        assertThat(findAnswer).isEqualTo(savedAnswer);
    }
}
