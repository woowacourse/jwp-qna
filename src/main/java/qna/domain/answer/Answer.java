package qna.domain.answer;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;
import qna.domain.EntityHistory;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.user.User;
import qna.exception.NotFoundException;
import qna.exception.UnAuthorizedException;

@Table(name = "answer")
@Entity
@Where(clause = "deleted=false")
public class Answer extends EntityHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question; // FK 관리되는 필드

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }
        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }
        this.id = id;
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistory delete() {
        this.deleted = true;
        return DeleteHistory.of(this);
    }

    public User getWriter() {
        return writer;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public Question getQuestion() {
        return question;
    }
}
