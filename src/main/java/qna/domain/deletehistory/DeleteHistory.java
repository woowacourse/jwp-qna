package qna.domain.deletehistory;

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
import javax.persistence.Table;
import qna.domain.DateHistory;
import qna.domain.answer.Answer;
import qna.domain.question.Question;
import qna.domain.user.User;

@Table(name = "delete_history")
@Entity
public class DeleteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contentId;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private LocalDateTime createDate = LocalDateTime.now();

    @JoinColumn(name = "deleted_by_id")
    @ManyToOne
    private User deleter;

    public DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, Long contentId, User deleter) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deleter = deleter;
    }

    public static DeleteHistory of(Question question) {
        return new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter());
    }

    public static DeleteHistory of(Answer answer) {
        return new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter());
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
        return Objects.equals(id, that.id)
                && Objects.equals(contentId, that.contentId)
                && contentType == that.contentType
                && Objects.equals(deleter, that.deleter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contentId, contentType, deleter);
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "id=" + id +
                ", contentId=" + contentId +
                ", contentType=" + contentType +
                ", createDate=" + createDate +
                ", deleter=" + deleter +
                '}';
    }
}
