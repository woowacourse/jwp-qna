package qna.domain.log;

import org.springframework.data.annotation.CreatedDate;
import qna.domain.content.ContentType;
import qna.domain.content.answer.Answer;
import qna.domain.content.question.Question;
import qna.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class DeleteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    private Long contentId;
    @ManyToOne
    @JoinColumn(name = "DELETED_BY_ID")
    private User deleteUser;
    @CreatedDate
    private LocalDateTime createDate;

    protected DeleteHistory() {
    }

    public DeleteHistory(Question question, User deleteUser, LocalDateTime createDate) {
        this(null, ContentType.QUESTION, question.getId(), deleteUser, createDate);
    }

    public DeleteHistory(Answer answer,User deleteUser, LocalDateTime createDate) {
        this(null, ContentType.ANSWER, answer.getId(), deleteUser, createDate);
    }

    public DeleteHistory(Long id, ContentType contentType, Long contentId, User deleteUser, LocalDateTime createDate) {
        this.id = id;
        this.contentType = contentType;
        this.contentId = contentId;
        this.deleteUser = deleteUser;
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
