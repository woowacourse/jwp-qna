package qna.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT q "
            + "FROM Question q "
            + "JOIN FETCH q.answers.answers a "
            + "WHERE  a.deleted = false")
    Optional<Question> findByIdAndDeletedFalse(Long id);
}
