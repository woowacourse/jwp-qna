package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @DisplayName("삭제 이력 생성")
    @Test
    void save() {
        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now());

        DeleteHistory actual = deleteHistories.save(expected);

        assertThat(actual).isEqualTo(expected);
    }
}
