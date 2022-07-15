package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    @DisplayName("삭제 정보를 저장한다.")
    void save() {
        final ContentType contentType = ContentType.QUESTION;
        final Long deleteContentId = 1L;
        final Long deleteUserId = 1L;
        final LocalDateTime deleteTime = LocalDateTime.now();
        final DeleteHistory deleteHistory
                = new DeleteHistory(contentType, deleteContentId, deleteUserId, deleteTime);

        final DeleteHistory saved = deleteHistoryRepository.save(deleteHistory);

        assertAll(
                () -> assertThat(saved).extracting("contentType", "contentId", "deletedById", "createDate")
                        .containsExactly(contentType, deleteContentId, deleteUserId, deleteTime),
                () -> assertThat(saved).isSameAs(deleteHistory)
        );
    }
}
