package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 1L);

        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        assertThat(savedDeleteHistory).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(deleteHistory);
    }

    @Test
    void findById() {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 1L);
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);
        entityManager.clear();

        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(savedDeleteHistory);
    }

    @Test
    void findAll() {
        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.ANSWER, 1L, 1L);
        DeleteHistory savedDeleteHistory1 = deleteHistoryRepository.save(deleteHistory1);
        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.QUESTION, 2L, 2L);
        DeleteHistory savedDeleteHistory2 = deleteHistoryRepository.save(deleteHistory2);
        entityManager.clear();

        List<DeleteHistory> result = deleteHistoryRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 1L);
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);
        entityManager.clear();

        deleteHistoryRepository.deleteById(savedDeleteHistory.getId());
        deleteHistoryRepository.flush();

        entityManager.clear();
        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isEmpty();
    }
}
