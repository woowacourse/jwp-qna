package study;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoGeneratedIdStationRepository extends JpaRepository<NoGeneratedIdStation, Long> {
}
