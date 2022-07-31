package qna.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.LastModifiedDate;
import qna.CannotDeleteException;

@Entity
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(name = "writer_id", foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Embedded
    private Answers answers = new Answers();

    @Column(nullable = false)
    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this(null, title, contents, null);
    }

    public Question(Long id, String title, String contents, User writer) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public Question writeBy(User writer) {
        this.writer = writer;
        return this;
    }

    public DeleteHistories deleteAndCreateDeleteHistories(User loginUser) throws CannotDeleteException {
        DeleteHistory questionDeleteHistory = this.deleteSoft(loginUser);
        DeleteHistories answersDeleteHistories = answers.deleteSoft(loginUser);
        return DeleteHistories.from(questionDeleteHistory, answersDeleteHistories);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.toQuestion(this);
    }

    public DeleteHistory deleteSoft(User loginUser) {
        validate(loginUser);
        this.deleted = true;
        return new DeleteHistory(ContentType.QUESTION, getId(), getWriter());
    }

    private void validate(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (this.deleted) {
            throw new CannotDeleteException("이미 삭제된 질문입니다.");
        }
        answers.validateAnswerNotExists(loginUser);
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Answer> getAnswers() {
        return answers.getValues();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", updatedAt=" + updatedAt +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                ", answers=" + answers +
                ", deleted=" + deleted +
                '}';
    }
}
