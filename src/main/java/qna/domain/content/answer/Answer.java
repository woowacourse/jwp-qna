package qna.domain.content.answer;

import qna.domain.content.question.Question;
import qna.domain.user.User;
import qna.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "WRITER_ID")
    private User writer;
    private String contents;
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;
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
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
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
