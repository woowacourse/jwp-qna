package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DeleteHistoryRepositoryTest extends RepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private UserRepository users;

    private static User savedJavajigi;

    @BeforeEach
    void setUp() {
        User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        savedJavajigi = users.save(javajigi);
    }

    @DisplayName("삭제 이력 생성")
    @Test
    void save() {
        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, 1L, savedJavajigi, LocalDateTime.now());

        DeleteHistory actual = deleteHistories.save(expected);

        assertThat(actual).isEqualTo(expected);
    }
}
