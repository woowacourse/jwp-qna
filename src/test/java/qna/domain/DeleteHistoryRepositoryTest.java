package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.fixture.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
public class DeleteHistoryRepositoryTest {

    private DeleteHistoryRepository deleteHistories;
    private QuestionRepository questions;
    private UserRepository users;

    public DeleteHistoryRepositoryTest(
        DeleteHistoryRepository deleteHistories,
        QuestionRepository questions,
        UserRepository users) {
        this.deleteHistories = deleteHistories;
        this.questions = questions;
        this.users = users;
    }

    @Test
    @DisplayName("삭제 내역을 저장한다.")
    void save() {
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);
        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questions.save(question);
        savedQuestion.setDeleted(true);


        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, savedQuestion.getId(), savedUser);
        DeleteHistory saveDeleteHistory = deleteHistories.save(deleteHistory);

        assertThat(saveDeleteHistory.getId()).isNotNull();
    }
}
