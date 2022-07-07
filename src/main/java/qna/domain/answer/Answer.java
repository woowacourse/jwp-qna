package qna.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import qna.domain.DateHistory;
import qna.domain.deletehistory.DeleteHistory;
import qna.exception.UnAuthorizedException;

import java.util.Objects;
import qna.domain.user.User;

@Table(name = "answer")
@Entity
public class Answer extends DateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    public Answer() {
    }

    public Answer(User writer, String contents) {
        this(null, writer, contents);
    }

    public Answer(Long id, User writer, String contents) {
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }
        this.id = id;
        this.writer = writer;
        this.contents = contents;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistory delete() {
        this.deleted = true;
        return DeleteHistory.of(this);
    }
}
