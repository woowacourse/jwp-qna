package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("deleteHistory를 저장한다.")
    void save() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        users.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, user, LocalDateTime.now());
        deleteHistories.save(deleteHistory);

        assertThat(deleteHistories.findById(deleteHistory.getId()).get()).isEqualTo(deleteHistory);
    }
}