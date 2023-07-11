package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class AnswerTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;

    private User savedUser1;
    private User savedUser2;
    private Question savedQuestion1;
    private Question savedQuestion2;

    @BeforeEach
    void before() {
        savedUser1 = userRepository.save(new User("오션", "ocean", "ocean", "ocean@ocean"));
        savedUser2 = userRepository.save(new User("오션2", "ocean", "ocean", "ocean@ocean"));
        savedQuestion1 = questionRepository.save(new Question("제목", "내용"));
        savedQuestion2 = questionRepository.save(new Question("제목2", "내용2"));
    }

    @Test
    void saveAnswer() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "내용");
        answerRepository.save(answer);

        assertThat(answer.getId()).isNotNull();
    }

    @Test
    void findAnswer() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "내용");
        Answer answer2 = new Answer(savedUser2, savedQuestion2, "내용");
        answerRepository.save(answer);
        answerRepository.save(answer2);

        List<Answer> answers = answerRepository.findAll();

        assertThat(answers).hasSize(2);
        assertThat(answers).contains(answer, answer2);
    }

    @Test
    void findAnswerById() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "내용");
        answerRepository.save(answer);

        Answer findAnswer = answerRepository.findById(answer.getId()).get();

        assertThat(findAnswer).isEqualTo(answer);
    }

    @Test
    void deleteAnswer() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "내용");
        answerRepository.save(answer);

        userRepository.delete(savedUser1);

        assertThatThrownBy(() -> userRepository.flush())
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
