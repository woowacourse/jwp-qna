package qna.domain.deletehistory;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.domain.deletehistory.DeleteHistory;

public interface DeleteHistoryRepository extends JpaRepository<DeleteHistory, Long> {
}
