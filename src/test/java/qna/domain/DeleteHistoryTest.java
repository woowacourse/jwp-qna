package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class DeleteHistoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    @DisplayName("DeleteHistory 객체를 Jpa를 이용해 저장한다.")
    void persist() {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, 1L, LocalDateTime.now());
        entityManager.persist(deleteHistory);
        assertThat(deleteHistory).isEqualTo(entityManager.find(DeleteHistory.class, deleteHistory.getId()));
    }
}