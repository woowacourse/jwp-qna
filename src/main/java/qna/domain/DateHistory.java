package qna.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DateHistory {

    // @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}
