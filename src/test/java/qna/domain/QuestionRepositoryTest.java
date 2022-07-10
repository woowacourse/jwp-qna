package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        Question expect = new Question("title1", "contents1").writeBy(UserFixture.JAVAJIGI);
        Question actual = questions.save(expect);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(expect).isEqualTo(actual)
        );
    }

    @Test
    @DisplayName("식별자가 삭제되지 않았을 경우 정상적으로 조회된다")
    void findByIdAndDeletedFalse_not_deleted() {
        Question expect = QuestionFixture.Q1;
        Question saveQuestion = questions.save(expect);

        Optional<Question> findQuestion = questions.findByIdAndDeletedFalse(saveQuestion.getId());

        assertAll(
            () -> assertThat(findQuestion).isNotEmpty(),
            () -> assertThat(saveQuestion).isEqualTo(findQuestion.get())
        );
    }

    @Test
    @DisplayName("식별자가 삭제되었을 경우 정상적으로 작동하지 않는다.")
    void findByIdAndDeletedFalse_not_true() {
        Question expect = QuestionFixture.Q1;
        Question saveQuestion = questions.save(expect);

        saveQuestion.setDeleted(true);
        Optional<Question> findQuestion = questions.findByIdAndDeletedFalse(saveQuestion.getId());

        assertThat(findQuestion).isEmpty();
    }
}
