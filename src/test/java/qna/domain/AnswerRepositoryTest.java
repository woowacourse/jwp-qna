package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.utils.fixture.AnswerFixture;
import qna.utils.fixture.QuestionFixture;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Test
    @DisplayName("답변을 저장한다.")
    void save() {
        Answer expected = AnswerFixture.A1;
        Answer actual = answers.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 조회할시 정삭적으로 조회된다.")
    void findByIdAndDeletedFalse_not_deleted() {
        Answer expected = AnswerFixture.A1;
        Answer saved = answers.save(expected);

        Optional<Answer> found = answers.findByIdAndDeletedFalse(expected.getId());

        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 데이터가 조회되지 않는다.")
    void findByIdAndDeletedFalse_deleted() {
        Answer expected = AnswerFixture.A1;
        Answer saved = answers.save(expected);

        saved.setDeleted(true);

        Optional<Answer> found = answers.findByIdAndDeletedFalse(expected.getId());

        assertThat(found).isEmpty();
    }
}
