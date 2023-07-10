package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@DataJpaTest
@TestConstructor(autowireMode = ALL)
class DeleteHistoryRepositoryTest {

    private DeleteHistoryRepository deleteHistoryRepository;

    public DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistoryRepository) {
        this.deleteHistoryRepository = deleteHistoryRepository;
    }

    @Test
    void findById() {
        // given
        DeleteHistory saved = deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        // when
        Optional<DeleteHistory> found = deleteHistoryRepository.findById(saved.getId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get().getContentType()).isEqualTo(ContentType.ANSWER)
        );
    }

    @Test
    void findAll() {
        // given
        deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        deleteHistoryRepository.save(DeleteHistoryFixture.d2());
        // when
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();
        // then
        assertThat(deleteHistories.size()).isEqualTo(2);
    }

    @Test
    void countTest() {
        // given
        deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        deleteHistoryRepository.save(DeleteHistoryFixture.d2());
        // when
        long count = deleteHistoryRepository.count();
        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void deleteTest() {
        // given
        DeleteHistory saved = deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        // when
        deleteHistoryRepository.delete(saved);
        Optional<DeleteHistory> found = deleteHistoryRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void deleteByIdTest() {
        // given
        DeleteHistory saved = deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        // when
        deleteHistoryRepository.deleteById(saved.getId());
        Optional<DeleteHistory> found = deleteHistoryRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();

    }

    @Test
    void existsTest() {
        // given
        DeleteHistory saved = deleteHistoryRepository.save(DeleteHistoryFixture.d1());
        // when
        boolean isExists = deleteHistoryRepository.existsById(saved.getId());
        // then
        assertThat(isExists).isTrue();
    }
}
