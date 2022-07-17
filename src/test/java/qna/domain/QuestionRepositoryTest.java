package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.UserTest.JAVAJIGI;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question savedQuestion = questionRepository.save(Q1);

        assertThat(savedQuestion).usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(Q1);
    }

    @Test
    void findById() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question savedQuestion = questionRepository.save(Q1);
        entityManager.clear();

        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .ignoringFields("id", "answers")
                .isEqualTo(savedQuestion);
    }

    @Test
    void findByDeletedFalse() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question Q2 = new Question("title2", "content2", savedUser);
        Q1.setDeleted(true);
        questionRepository.save(Q1);
        questionRepository.save(Q2);
        entityManager.clear();

        List<Question> questionsDeletedFalse = this.questionRepository.findByDeletedFalse();

        assertThat(questionsDeletedFalse).hasSize(1);
    }

    @Test
    void findByIdAndDeletedFalseWithDeletedTrue() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question deletedQuestion = Q1;
        deletedQuestion.setDeleted(true);
        Question savedQuestion = questionRepository.save(deletedQuestion);
        entityManager.clear();

        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findAll() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question Q2 = new Question("title2", "content2", savedUser);
        Question savedQuestion1 = questionRepository.save(Q1);
        Question savedQuestion2 = questionRepository.save(Q2);
        entityManager.clear();

        List<Question> result = questionRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        User savedUser = userRepository.save(JAVAJIGI);
        Question Q1 = new Question("title1", "contents1", savedUser);
        Question savedQuestion = questionRepository.save(Q1);
        entityManager.clear();

        questionRepository.deleteById(savedQuestion.getId());
        questionRepository.flush();

        entityManager.clear();
        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isEmpty();
    }
}
