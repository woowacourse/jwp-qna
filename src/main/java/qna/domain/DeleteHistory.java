package qna.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "delete_history")
public class DeleteHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private ContentType contentType;
  @Column(name = "contentId")
  private Long contentId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;

  private Long deletedById;

  protected DeleteHistory() {
  }

  public DeleteHistory(ContentType contentType, Long contentId, LocalDateTime createDate) {
    this.contentType = contentType;
    this.contentId = contentId;
    this.createDate = createDate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, contentType, contentId);
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
        Objects.equals(contentId, that.contentId);
  }

  @Override
  public String toString() {
    return "DeleteHistory{" +
        "id=" + id +
        ", contentType=" + contentType +
        ", contentId=" + contentId +
        '}';
  }

  public Long getId() {
    return id;
  }

  public ContentType getContentType() {
    return contentType;
  }

  public Long getContentId() {
    return contentId;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public Long getDeletedById() {
    return deletedById;
  }
}
