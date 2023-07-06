package subway;

import org.springframework.data.jpa.repository.JpaRepository;

interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name); // (1) 값이 존재하지 않을 수 있으면 Optional<Station> 사용함
}

