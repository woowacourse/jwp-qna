package qna.domain.content.answer;

import qna.domain.content.Content;
import qna.domain.content.question.Question;
import qna.domain.user.User;
import qna.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "WRITER_ID")
    private User writer;
    @Lob
    private String contents;
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
    @Column(nullable = false)
    private boolean deleted;

    protected Answer() {
    }

    public Answer(User writer, String contents) {
        this(null, writer, contents);
    }

    public Answer(Long id, User writer, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        this.writer = writer;
        this.contents = contents;
    }

    public void setQuestion(Question question) {
        if(this.question != null) {
            this.question.removeAnswer(this);
        }

        this.question = question;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void toDeleted() {
        this.question = null;
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
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
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
