package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@DataJpaTest
@TestConstructor(autowireMode = ALL)
class QuestionRepositoryTest {

    private QuestionRepository questionRepository;

    public QuestionRepositoryTest(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Test
    void findById() {
        // given
        Question saved = questionRepository.save(QuestionFixture.Q1);
        // when
        Optional<Question> found = questionRepository.findById(saved.getId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get().getContents()).isEqualTo(QuestionFixture.Q1.getContents())
        );
    }

    @Test
    void findAll() {
        // given
        questionRepository.save(QuestionFixture.Q1);
        questionRepository.save(QuestionFixture.Q2);
        // when
        List<Question> questions = questionRepository.findAll();
        // then
        assertThat(questions).hasSize(2);
    }

    @Test
    void countTest() {
        // given
        questionRepository.save(QuestionFixture.Q1);
        questionRepository.save(QuestionFixture.Q2);
        // when
        long count = questionRepository.count();
        // then
        assertThat(count).isEqualTo(2);

    }

    @Test
    void deleteTest() {
        // given
        Question saved = questionRepository.save(QuestionFixture.Q1);
        // when
        questionRepository.delete(saved);
        Optional<Question> found = questionRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void deleteByIdTest() {
        // given
        Question saved = questionRepository.save(QuestionFixture.Q1);
        // when
        questionRepository.deleteById(saved.getId());
        Optional<Question> found = questionRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void existsTest() {
        // given
        Question saved = questionRepository.save(QuestionFixture.Q1);
        // when
        boolean isExists = questionRepository.existsById(saved.getId());
        // then
        assertThat(isExists).isTrue();
    }

    @Test
    void findByDeletedFalse() {
        // given
        questionRepository.save(QuestionFixture.Q1);
        questionRepository.save(QuestionFixture.Q2);
        // when
        List<Question> found = questionRepository.findByDeletedFalse();
        // then
        assertThat(found).hasSize(2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        // given
        Question saved = questionRepository.save(QuestionFixture.Q1);
        questionRepository.save(QuestionFixture.Q2);
        // when
        Optional<Question> found = questionRepository.findByIdAndDeletedFalse(saved.getId());
        // then
        assertThat(found).isPresent();
    }
}
