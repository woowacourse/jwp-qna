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
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    @DisplayName("삭제 기록을 저장한다.")
    void save() {
        // given
        final DeleteHistory deleteHistory = new DeleteHistory(
            ContentType.ANSWER,
            1L,
            1L,
            LocalDateTime.now()
        );

        // when
        final DeleteHistory actualDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        // then
        assertThat(actualDeleteHistory.getId()).isNotNull();
    }
}
