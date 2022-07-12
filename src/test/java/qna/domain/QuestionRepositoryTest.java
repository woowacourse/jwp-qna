package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.utils.fixture.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class QuestionRepositoryTest {

    private QuestionRepository questions;
    private UserRepository users;

    public QuestionRepositoryTest(QuestionRepository questions, UserRepository users) {
        this.questions = questions;
        this.users = users;
    }

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);

        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question actual = questions.save(expect);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 조회할시 정삭적으로 조회된다.")
    void findByIdAndDeletedFalse_not_deleted() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);

        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question actual = questions.save(expect);

        Optional<Question> found = questions.findByIdAndDeletedFalse(actual.getId());

        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("식별자와 삭제되지 않은 조건으로 데이터가 조회되지 않는다.")
    void findByIdAndDeletedFalse_deleted() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);

        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questions.save(question);

        savedQuestion.setDeleted(true);

        Optional<Question> found = questions.findByIdAndDeletedFalse(savedQuestion.getId());

        assertThat(found).isEmpty();
    }
}
