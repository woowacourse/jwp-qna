package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티_타입, 식별자_타입>
public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);
}
