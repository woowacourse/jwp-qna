package qna.domain;

import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;
import qna.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        validate(writer, question);
        this.id = id;
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    private void validate(User writer, Question question) {
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }
        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }
    }

    public DeleteHistory deleteBy(User user) {
        if (this.isNotOwner(user)) {
            throw new CannotDeleteException("답변을 삭제할 권한이 없습니다.");
        }

        this.deleted = true;

        return DeleteHistory.ofAnswer(id, user);
    }

    private boolean isNotOwner(User writer) {
        return !this.writer.equals(writer);
    }
    
    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }
}
