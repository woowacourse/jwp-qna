package qna.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class DeleteHistory extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private ContentType contentType;
    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by_id", foreignKey = @ForeignKey(name = "fk_delete_history_to_user"))
    private User deletedBy;

    @CreatedDate
    private LocalDateTime createDate;

    protected DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, Long contentId, User deletedBy, LocalDateTime createDate) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.createDate = createDate;
        this.deletedBy = deletedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id) &&
            contentType == that.contentType &&
            Objects.equals(contentId, that.contentId) &&
            Objects.equals(deletedBy.id, that.deletedBy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentType, contentId, deletedBy.id);
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
            "id=" + id +
            ", contentType=" + contentType +
            ", contentId=" + contentId +
            ", deletedById=" + deletedBy.id +
            ", createDate=" + createDate +
            '}';
    }
}
