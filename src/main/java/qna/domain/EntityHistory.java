package qna.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityHistory {

    @Column(nullable = false)
    protected LocalDateTime createdAt = LocalDateTime.now();

    protected LocalDateTime updatedAt;
}
