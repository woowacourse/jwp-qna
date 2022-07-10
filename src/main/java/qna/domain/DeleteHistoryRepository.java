package qna.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteHistoryRepository extends JpaRepository<DeleteHistory, Long> {
    List<DeleteHistory> findDeleteHistoriesByContentType(ContentType contentType);

    List<DeleteHistory> findDeleteHistoriesByCreateDateBetween(LocalDateTime from, LocalDateTime to);
}
