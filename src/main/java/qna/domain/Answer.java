package qna.domain;

import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Answer extends MappedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User writer;
    @ManyToOne
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
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.toQuestion(question);
        this.toWriter(writer);
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writer.getId().equals(writer.getId());
    }

    public void toQuestion(Question question) {
        if (this.question != null) {
            this.question.deleteAnswer(this);
        }
        this.question = question;
        question.addAnswer(this);
    }

    public void toWriter(User user) {
        this.writer = user;
        user.addAnswer(this);
    }

    public Long getId() {
        return id;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getQuestionId() {
        return this.question.getId();
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public User getWriter() {
        return writer;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writerId=" + writer.getId() +
                ", questionId=" + question.getId() +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
