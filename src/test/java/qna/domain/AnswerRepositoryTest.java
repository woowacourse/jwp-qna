package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@DataJpaTest
@TestConstructor(autowireMode = ALL)
class AnswerRepositoryTest {

    private AnswerRepository answerRepository;

    public AnswerRepositoryTest(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Test
    void findById() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        Optional<Answer> found = answerRepository.findById(saved.getId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get().getContents()).isEqualTo(AnswerFixture.a1().getContents())
        );
    }

    @Test
    void findAll() {
        // given
        answerRepository.save(AnswerFixture.a1());
        answerRepository.save(AnswerFixture.a2());
        // when
        List<Answer> answers = answerRepository.findAll();
        // then
        assertThat(answers.size()).isEqualTo(2);
    }

    @Test
    void countTest() {
        // given
        answerRepository.save(AnswerFixture.a1());
        answerRepository.save(AnswerFixture.a2());
        // when
        long count = answerRepository.count();
        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void deleteTest() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        answerRepository.delete(saved);
        Optional<Answer> found = answerRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void deleteByIdTest() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        answerRepository.deleteById(saved.getId());
        Optional<Answer> found = answerRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();

    }

    @Test
    void existsTest() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        boolean isExists = answerRepository.existsById(saved.getId());
        // then
        assertThat(isExists).isTrue();
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(AnswerFixture.a1().getQuestionId());
        // then
        assertAll(
                () -> assertThat(answers.size()).isEqualTo(1),
                () -> assertThat(answers.get(0)).isSameAs(saved)
        );
    }

    @Test
    void findByIdAndDeletedFalse() {
        // given
        Answer saved = answerRepository.save(AnswerFixture.a1());
        // when
        Optional<Answer> found = answerRepository.findByIdAndDeletedFalse(saved.getId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get()).isSameAs(saved)
        );
    }
}
