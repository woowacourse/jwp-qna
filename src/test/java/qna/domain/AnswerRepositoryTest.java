package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.AnswerFixture;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

@DataJpaTest
public class AnswerRepositoryTest {

    @Autowired
    private UserRepository users;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private AnswerRepository answers;

    @Test
    @DisplayName("답변을 저장한다.")
    void save() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question question = QuestionFixture.Q1.writeBy(savedUser);
        Question savedQuestion = questions.save(question);

        Answer expect = new Answer(savedUser, savedQuestion, "Answers Contents1");
        Answer actual = answers.save(expect);
        assertThat(expect).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(actual);
    }

    @Test
    @DisplayName("식별자가 삭제되지 않은 조건으로 조회할시 정삭적으로 조회된다.")
    void findByIdAndDeletedFalse_not_deleted() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question question = QuestionFixture.Q1.writeBy(savedUser);
        Question savedQuestion = questions.save(question);

        Answer answer = new Answer(savedUser, savedQuestion, "Answers Contents1");
        answers.save(answer);

        Optional<Answer> found = answers.findByIdAndDeletedFalse(savedQuestion.getId());

        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("식별자와 삭제된 조건으로 조회할시 정상적으로 조회되지 않는다")
    void findByIdAndDeletedFalse_not_true() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question question = QuestionFixture.Q1.writeBy(savedUser);
        Question savedQuestion = questions.save(question);

        Answer answer = new Answer(savedUser, savedQuestion, "Answers Contents1");
        Answer savedAnswer = answers.save(answer);

        savedAnswer.setDeleted(true);
        Optional<Answer> found = answers.findByIdAndDeletedFalse(savedAnswer.getId());

        assertThat(found).isEmpty();
    }
}
