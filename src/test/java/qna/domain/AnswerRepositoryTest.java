package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.AnswerFixture;

@DataJpaTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Test
    @DisplayName("답변을 저장한다.")
    void save() {
        Answer expect = AnswerFixture.A1;
        Answer actual = answers.save(expect);
        assertThat(expect).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(actual);
    }

    @Test
    @DisplayName("식별자가 삭제되지 않은 조건으로 조회할시 정삭적으로 조회된다.")
    void findByIdAndDeletedFalse_not_deleted() {
        Answer expect = AnswerFixture.A1;
        Answer saveQuestion = answers.save(expect);

        Optional<Answer> found = answers.findByIdAndDeletedFalse(saveQuestion.getId());

        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("식별자와 삭제된 조건으로 조회할시 정상적으로 조회되지 않는다")
    void findByIdAndDeletedFalse_not_true() {
        Answer expect = AnswerFixture.A1;
        Answer saveQuestion = answers.save(expect);

        saveQuestion.setDeleted(true);
        Optional<Answer> found = answers.findByIdAndDeletedFalse(saveQuestion.getId());

        assertThat(found).isEmpty();
    }
}
