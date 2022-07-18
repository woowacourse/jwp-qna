package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager entityManager;

    User savedUser1, savedUser2;
    Question savedQuestion1, savedQuestion2;

    @BeforeEach
    void setUp() {
        savedUser1 = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser1);
        savedQuestion1 = questionRepository.save(Q1);

        savedUser2 = userRepository.save(SANJIGI);
        Question Q2 = new Question("title2", "contents2", savedUser2);
        savedQuestion2 = questionRepository.save(Q2);
    }

    @Test
    void save() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "AnswerContent");

        Answer savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(answer);
    }

    @Test
    void findById() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "AnswerContent");
        Answer savedAnswer = answerRepository.save(answer);
        entityManager.clear();

        Optional<Answer> result = answerRepository.findById(answer.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .ignoringFields("question")
                .isEqualTo(savedAnswer);
    }

    @Test
    void findAll() {
        Answer answer1 = new Answer(savedUser1, savedQuestion1, "AnswerContent");
        answerRepository.save(answer1);
        Answer answer2 = new Answer(savedUser2, savedQuestion2, "AnswerContent");
        answerRepository.save(answer2);
        entityManager.clear();

        List<Answer> result = answerRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        Answer answer = new Answer(savedUser1, savedQuestion1, "AnswerContent");
        Answer savedAnswer = answerRepository.save(answer);
        entityManager.clear();

        answerRepository.deleteById(savedAnswer.getId());
        answerRepository.flush();

        entityManager.clear();
        Optional<Answer> result = answerRepository.findById(savedAnswer.getId());

        assertThat(result).isEmpty();
    }
}
