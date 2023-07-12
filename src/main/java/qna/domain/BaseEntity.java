package qna.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntity {

    @NotNull
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
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
