package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.utils.fixture.QuestionFixture;
import qna.utils.fixture.UserFixture;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;


    @Test
    @DisplayName("삭제 내역을 저장한다")
    void test() {
        // given
        Question expect = QuestionFixture.Q1;
        Question savedQuestion = questions.save(expect);
        savedQuestion.setDeleted(true);

        User user = UserFixture.JAVAJIGI;
        User savedUser = users.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, savedQuestion.getId(), savedUser.getId(),
                LocalDateTime.now());

        DeleteHistory save = deleteHistories.save(deleteHistory);

        // then
        assertThat(save.getId()).isNotNull();
    }
}
