package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.config.JPAConfig;
import qna.utils.fixture.UserFixture;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@Import(JPAConfig.class)
class DeleteHistoryRepositoryTest {

    private DeleteHistoryRepository deleteHistories;
    private QuestionRepository questions;
    private UserRepository users;

    public DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistories, QuestionRepository questions,
                                       UserRepository users) {
        this.deleteHistories = deleteHistories;
        this.questions = questions;
        this.users = users;
    }

    @Test
    @DisplayName("삭제 내역을 저장한다")
    void test() {
        // given
        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);

        Question expect = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questions.save(expect);
        savedQuestion.delete(savedUser);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, savedQuestion.getId(), savedUser);

        DeleteHistory save = deleteHistories.save(deleteHistory);

        // then
        assertThat(save.getId()).isNotNull();
    }
}
