package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;

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
    private EntityManager entityManager;

    @Test
    void save() {
        Question savedQuestion = questionRepository.save(Q1);

        assertThat(savedQuestion).usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(Q1);
    }

    @Test
    void findById() {
        Question savedQuestion = questionRepository.save(Q1);
        entityManager.clear();

        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(savedQuestion);
    }

    @Test
    void findByDeletedFalse() {
        questionRepository.save(Q1);
        questionRepository.save(Q2);
        Question savedQuestion = questionRepository.save(new Question("제목", "내용"));
        savedQuestion.setDeleted(true);
        entityManager.clear();

        List<Question> questionsDeletedFalse = this.questionRepository.findByDeletedFalse();

        assertThat(questionsDeletedFalse).hasSize(2);
    }

    @Test
    void findByIdAndDeletedFalseWithDeletedTrue() {
        Question deletedQuestion = Q1;
        deletedQuestion.setDeleted(true);
        Question savedQuestion = questionRepository.save(deletedQuestion);
        entityManager.clear();

        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findAll() {
        Question savedQuestion1 = questionRepository.save(Q1);
        Question savedQuestion2 = questionRepository.save(Q2);
        entityManager.clear();

        List<Question> result = questionRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        Question savedQuestion = questionRepository.save(Q1);
        entityManager.clear();

        questionRepository.deleteById(savedQuestion.getId());
        questionRepository.flush();

        entityManager.clear();
        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isEmpty();
    }
}
