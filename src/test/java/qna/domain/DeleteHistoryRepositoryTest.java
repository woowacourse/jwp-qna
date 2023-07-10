package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void save() {
        // given
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now());

        // when
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        // then
        assertAll(
                () -> assertThat(savedDeleteHistory).isNotNull(),
                () -> assertThat(savedDeleteHistory.getContentType()).isEqualTo(ContentType.QUESTION),
                () -> assertThat(savedDeleteHistory.getContentId()).isEqualTo(1L),
                () -> assertThat(savedDeleteHistory.getDeletedById()).isEqualTo(1L)
        );
    }

    @Test
    void findAll() {
        // given
        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now());
        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.ANSWER, 2L, 2L, LocalDateTime.now());

        deleteHistoryRepository.save(deleteHistory1);
        deleteHistoryRepository.save(deleteHistory2);

        // when
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();

        // then
        assertAll(
                () -> assertThat(deleteHistories).hasSize(2),
                () -> assertThat(deleteHistories.get(0)).usingRecursiveComparison().isEqualTo(deleteHistory1),
                () -> assertThat(deleteHistories.get(1)).usingRecursiveComparison().isEqualTo(deleteHistory2)
        );
    }

    @Test
    void delete() {
        // given
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now());
        deleteHistoryRepository.save(deleteHistory);

        // when
        deleteHistoryRepository.delete(deleteHistory);

        // then
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();
        assertThat(deleteHistories).isEmpty();
    }

}