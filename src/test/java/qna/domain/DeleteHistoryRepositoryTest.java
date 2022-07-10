package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaConfig;

@DataJpaTest
@Import(JpaConfig.class)
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @DisplayName("저장하고 리턴된 객체는 저장하기전의 객체와 참조 값이 같은 객체이다.")
    @Test
    void save() {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 2L, LocalDateTime.now());
        DeleteHistory saved = deleteHistoryRepository.save(deleteHistory);

        assertThat(deleteHistory).isEqualTo(saved);
    }
}
