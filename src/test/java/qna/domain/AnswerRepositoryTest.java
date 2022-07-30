package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import qna.JpaAuditingConfiguration;

@DataJpaTest
@Import(JpaAuditingConfiguration.class)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("questionTitle", "contents", savedUser);
        Question savedQuestion = questionRepository.save(Q1);
        Answer answer = new Answer(savedUser, savedQuestion, "answerContent");

        Answer savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(answer);
    }

    @Test
    void findById() {
        Answer savedAnswer = createAnswer(JAVAJIGI);
        synchronize();

        Optional<Answer> result = answerRepository.findById(savedAnswer.getId());

        assertThat(result).isPresent();
    }

    @Test
    void findAll() {
        Answer answer1 = createAnswer(JAVAJIGI);
        Answer answer2 = createAnswer(SANJIGI);
        synchronize();

        List<Answer> result = answerRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        Answer savedAnswer = createAnswer(SANJIGI);
        synchronize();

        answerRepository.deleteById(savedAnswer.getId());
        synchronize();

        Optional<Answer> result = answerRepository.findById(savedAnswer.getId());
        assertThat(result).isEmpty();
    }

    Answer createAnswer(User user) {
        User savedUser = userRepository.save(user);
        Question Q1 = new Question("questionTitle", "contents", savedUser);
        Question savedQuestion = questionRepository.save(Q1);
        Answer answer = new Answer(savedUser, savedQuestion, "answerContent");
        return answerRepository.save(answer);
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
