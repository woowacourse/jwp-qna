package qna.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.JpaConfig;

import java.time.LocalDateTime;

@DataJpaTest
@Import(JpaConfig.class)
class DeleteHistoryRepositoryTest {

    @Autowired
    DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void DeleteHistory를_저장하고_조회한다() {
        DeleteHistory history = new DeleteHistory(ContentType.QUESTION, 1L, 2L, LocalDateTime.now());
        DeleteHistory save = deleteHistoryRepository.save(history);

        DeleteHistory saved = deleteHistoryRepository.findById(save.getId()).get();

        Assertions.assertThat(save).isSameAs(saved);
    }
}
