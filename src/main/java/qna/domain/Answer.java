package qna.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import qna.NotFoundException;
import qna.UnAuthorizedException;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long writerId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "question_id",
            foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

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

        this.writerId = writer.getId();
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writerId.equals(writer.getId());
    }

    public void update(Answer updatedAnswer) {
        if (!this.writerId.equals(updatedAnswer.writerId)) {
            throw new IllegalArgumentException("작성자가 달라 답변을 수정할 수 없습니다.");
        }
        this.contents = updatedAnswer.contents;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writerId=" + writerId +
                ", questionId=" + question +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
