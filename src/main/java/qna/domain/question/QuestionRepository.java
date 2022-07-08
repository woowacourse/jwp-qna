package qna.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import qna.domain.question.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByDeletedFalse();

    Optional<Question> findByIdAndDeletedFalse(Long id);
}
