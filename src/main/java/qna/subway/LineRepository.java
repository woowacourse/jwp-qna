package qna.subway;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {

    Optional<Line> findByName(final String name);
}
