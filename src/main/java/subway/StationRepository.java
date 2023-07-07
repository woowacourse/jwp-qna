package subway;

import org.springframework.data.jpa.repository.JpaRepository;

interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(final String name); // (1)
}
