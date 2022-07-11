package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

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

        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedQuestion);
    }

    @Test
    void findAll() {
        Question savedQuestion1 = questionRepository.save(Q1);
        Question savedQuestion2 = questionRepository.save(Q2);

        List<Question> result = questionRepository.findAll();

        assertThat(result).contains(savedQuestion1, savedQuestion2);
    }

    @Test
    void deleteById() {
        Question savedQuestion = questionRepository.save(Q1);

        questionRepository.deleteById(savedQuestion.getId());
        Optional<Question> result = questionRepository.findById(savedQuestion.getId());

        assertThat(result).isEmpty();
    }
}
