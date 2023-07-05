package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.DeleteHistoryFixture.D1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DeleteHistoryRepositoryTest {

    private final DeleteHistoryRepository deleteHistoryRepository;

    public DeleteHistoryRepositoryTest(final DeleteHistoryRepository deleteHistoryRepository) {
        this.deleteHistoryRepository = deleteHistoryRepository;
    }

    @DisplayName("삭제내역을 저장한다.")
    @Test
    void save() {
        // given
        // when
        final DeleteHistory saved = deleteHistoryRepository.save(D1);

        // then
        assertThat(deleteHistoryRepository.findById(saved.getId()).get())
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("식별자로 조회한 질문은 서로 동일하다.")
    @Test
    void identity() {
        // given
        final DeleteHistory saved = deleteHistoryRepository.save(D1);
        final DeleteHistory actual = deleteHistoryRepository.findById(saved.getId()).get();

        // when
        // then
        assertThat(saved == actual).isTrue();
    }
}


