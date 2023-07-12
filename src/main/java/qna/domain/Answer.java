package qna.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import qna.NotFoundException;
import qna.UnAuthorizedException;

@Entity
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;
    @Lob
    @NotNull
    private String contents;
    private boolean deleted = false;

    protected Answer() {
    }

    public Answer(final User writer, final Question question, final String contents) {
        this(null, writer, question, contents);
    }

    public Answer(final Long id, final User writer, final Question question, final String contents) {
        this.id = id;
        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }
        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(final User writer) {
        return this.writer.equals(writer);
    }

    public void toQuestion(final Question question) {
        this.question = question;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Answer answer = (Answer) other;
        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + id +
            ", writer=" + writer +
            ", question=" + question +
            ", contents='" + contents + '\'' +
            ", deleted=" + deleted +
            '}';
    }
}
