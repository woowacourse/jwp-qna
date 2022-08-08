package qna.domain.deletehistory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import qna.annotation.DatabaseTest;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DatabaseTest
class DeleteHistoryTest {

    @Autowired
    private UserRepository users;

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Test
    void 동일한_타입의_동일한_id의_데이터를_제거한_기록을_중복으로_저장하려는_경우_DB_차원에서_예외발생() {
        User user = users.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        deleteHistories.save(new DeleteHistory(ContentType.QUESTION, 1L, user));

        assertThatThrownBy(() -> deleteHistories.save(new DeleteHistory(ContentType.QUESTION, 1L, user)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
