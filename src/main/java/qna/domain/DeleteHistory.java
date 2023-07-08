package qna.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class DeleteHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(value = EnumType.STRING)
  private ContentType contentType;
  private Long contentId;
  @ManyToOne
  @JoinColumn(name = "deleted_by_id")
  private User deleteUser;
  @CreatedDate
  private LocalDateTime createDate;

  protected DeleteHistory() {
  }

  public DeleteHistory(final ContentType contentType, final Long contentId, final User deleteUser,
      final LocalDateTime createDate) {
    this.contentType = contentType;
    this.contentId = contentId;
    this.deleteUser = deleteUser;
    this.createDate = createDate;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final DeleteHistory that = (DeleteHistory) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
