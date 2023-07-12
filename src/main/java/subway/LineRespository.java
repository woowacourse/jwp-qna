package subway;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRespository extends JpaRepository<Line, Long> {
    Line findByName(String name);
}
