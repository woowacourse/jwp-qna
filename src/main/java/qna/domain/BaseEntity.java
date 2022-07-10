package qna.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @LastModifiedDate
    @Column
    private LocalDateTime updateAt;

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }
}
