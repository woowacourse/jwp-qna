package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.fixture.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
public class QuestionRepositoryTest {

    private UserRepository users;
    private QuestionRepository questions;

    public QuestionRepositoryTest(UserRepository users, QuestionRepository questions) {
        this.users = users;
        this.questions = questions;
    }

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question actual = questions.save(expect);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(expect).isEqualTo(actual)
        );
    }

    @Test
    @DisplayName("식별자가 삭제되지 않았을 경우 정상적으로 조회된다")
    void findByIdAndDeletedFalse_not_deleted() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questions.save(expect);

        Optional<Question> findQuestion = questions.findByIdAndDeletedFalse(savedQuestion.getId());

        assertAll(
            () -> assertThat(findQuestion).isNotEmpty(),
            () -> assertThat(savedQuestion).isEqualTo(findQuestion.get())
        );
    }

    @Test
    @DisplayName("식별자가 삭제되었을 경우 정상적으로 작동하지 않는다.")
    void findByIdAndDeletedFalse_not_true() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questions.save(expect);

        savedQuestion.setDeleted(true);
        Optional<Question> findQuestion = questions.findByIdAndDeletedFalse(savedQuestion.getId());

        assertThat(findQuestion).isEmpty();
    }
}
