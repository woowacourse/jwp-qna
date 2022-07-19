package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
class DeleteHistoryRepositoryTest extends RepositoryTest {

    private static User savedJavajigi;

    private final DeleteHistoryRepository deleteHistories;
    private final UserRepository users;

    public DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistories, UserRepository users) {
        this.deleteHistories = deleteHistories;
        this.users = users;
    }

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
