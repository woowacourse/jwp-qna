package qna.domain.deletehistory;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import qna.domain.ContentType;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("deleteHistory를 저장한다.")
    void saveA() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        users.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, user, LocalDateTime.now());

        deleteHistories.save(deleteHistory);

        assertThat(deleteHistories.findById(deleteHistory.getId())).isEqualTo(Optional.of(deleteHistory));
    }

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