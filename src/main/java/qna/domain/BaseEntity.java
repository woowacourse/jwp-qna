package qna.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    public BaseEntity() {
        final LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
