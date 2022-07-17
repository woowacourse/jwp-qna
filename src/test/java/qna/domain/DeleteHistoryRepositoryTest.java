package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DeleteHistoryRepositoryTest extends CashManager {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private UserRepository users;

    private static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");

    private static User SAVED_JAVAJIGI;

    @BeforeEach
    void setUp() {
        SAVED_JAVAJIGI = users.save(JAVAJIGI);
    }

    @DisplayName("삭제 이력 생성")
    @Test
    void save() {
        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, 1L, SAVED_JAVAJIGI, LocalDateTime.now());

        DeleteHistory actual = deleteHistories.save(expected);

        assertThat(actual).isEqualTo(expected);
    }
}
