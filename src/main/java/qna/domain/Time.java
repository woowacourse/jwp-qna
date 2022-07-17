package qna.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Time {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    protected void updateDate() {
        updateDate = LocalDateTime.now();
    }
}
