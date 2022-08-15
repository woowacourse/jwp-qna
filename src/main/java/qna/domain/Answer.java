package qna.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import qna.CannotDeleteException;
import qna.NotFoundException;
import qna.UnAuthorizedException;

@Entity
public class Answer extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

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

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public Answer(final Long id,
                  final User writer,
                  final Question question,
                  final String contents,
                  final boolean deleted) {
        this.id = id;
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.deleted = deleted;
    }

    protected Answer() {
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistory deleteBy(User user) {
        checkIsNotDeleted();
        checkIsWrittenBy(user);

        this.deleted = true;
        return DeleteHistory.from(this);
    }

    private void checkIsNotDeleted() {
        if (deleted) {
            throw new CannotDeleteException("이미 삭제된 답변입니다.");
        }
    }

    private void checkIsWrittenBy(User user) {
        if (!this.writer.equals(user)) {
            throw new CannotDeleteException("답변은 작성자 본인만 삭제할 수 있습니다");
        }
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
