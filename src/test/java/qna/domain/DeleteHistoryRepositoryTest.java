package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("모든 deleteHistory를 저장한다.")
    void saveAll() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        users.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, user, LocalDateTime.now());
        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, 1L, user, LocalDateTime.now());

        List<DeleteHistory> deleteHistoryList = new ArrayList<>();
        deleteHistoryList.add(deleteHistory);
        deleteHistoryList.add(deleteHistory1);
        deleteHistories.saveAll(deleteHistoryList);

        assertThat(deleteHistories.findAll()).isEqualTo(deleteHistoryList);
    }
}