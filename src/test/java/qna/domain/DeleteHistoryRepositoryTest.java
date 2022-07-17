package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

@DataJpaTest
public class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

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
