package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerFixture.A1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;

    public AnswerRepositoryTest(final AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @DisplayName("답변을 저장한다.")
    @Test
    void save() {
        // given
        // when
        final Answer saved = answerRepository.save(A1);

        // then
        assertThat(answerRepository.findById(saved.getId()).get())
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("해당 질문 아이디의, 삭제되지 않은 모든 답변을 조회한다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        final Answer saved = answerRepository.save(A1);

        // when
        final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(saved.getQuestionId());

        // then
        assertThat(actual)
                .contains(saved);
    }

    @DisplayName("삭제되지 않은 답변을 식별자 아이디로 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        // given
        final Answer saved = answerRepository.save(A1);

        // when
        final Answer actual = answerRepository.findByIdAndDeletedFalse(saved.getId()).get();

        // then
        assertThat(actual == saved).isTrue();
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("답변의 삭제 여부 변경을 감지해 갱신한다.")
    @Test
    void updateDeleted() {
        // given
        final Answer saved = answerRepository.save(A1);

        // when
        saved.setDeleted(true);

        // then
        final Answer updated = answerRepository.findById(saved.getId()).get();
        assertThat(updated.isDeleted())
                .isTrue();
    }

    @DisplayName("식별자로 조회한 답변은 서로 동일하다.")
    @Test
    void identity() {
        // given
        final Answer saved = answerRepository.save(A1);
        final Answer actual = answerRepository.findById(saved.getId()).get();
        final Answer actual2 = answerRepository.findByIdAndDeletedFalse(saved.getId()).get();

        // when
        // then
        assertThat(saved == actual).isTrue();
        assertThat(actual == actual2).isTrue();
    }
}
