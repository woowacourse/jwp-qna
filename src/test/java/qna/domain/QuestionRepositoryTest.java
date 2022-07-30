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
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question question = new Question("title1", "contents1", savedUser);
        Question savedQuestion = questionRepository.save(question);

        assertThat(savedQuestion).usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(question);
    }

    @Test
    void findById() {
        Question savedQuestion = createQuestion(JAVAJIGI);
        synchronize();

        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(savedQuestion);
    }

    @Test
    void findByDeletedFalse() {
        Question savedQuestion1 = createQuestion(JAVAJIGI);
        savedQuestion1.setDeleted(true);
        Question savedQuestion2 = createQuestion(SANJIGI);
        synchronize();

        List<Question> questionsDeletedFalse = this.questionRepository.findByDeletedFalse();

        assertThat(questionsDeletedFalse).hasSize(1);
    }

    @Test
    void findByIdAndDeletedFalseWithDeletedTrue() {
        Question savedQuestion = createQuestion(JAVAJIGI);
        savedQuestion.setDeleted(true);
        synchronize();

        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findAll() {
        Question savedQuestion1 = createQuestion(JAVAJIGI);
        Question savedQuestion2 = createQuestion(SANJIGI);
        synchronize();

        List<Question> result = questionRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        Question savedQuestion = createQuestion(JAVAJIGI);
        synchronize();

        questionRepository.deleteById(savedQuestion.getId());
        synchronize();

        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isEmpty();
    }

    Question createQuestion(User user) {
        User savedUser = userRepository.save(user);
        Question Q = new Question("title1", "contents1", savedUser);
        return questionRepository.save(Q);
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
