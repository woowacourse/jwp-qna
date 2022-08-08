package qna.domain.question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import qna.domain.EntityHistory;
import qna.domain.answer.Answer;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.user.User;
import qna.exception.AlreadyDeletedException;
import qna.exception.CannotDeleteException;

@Table(name = "question")
@Entity
@SQLDelete(sql = "UPDATE question SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Question extends EntityHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false, length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    protected Question() {
    }

    public Question(String title, String contents, User writer) {
        this(null, title, contents, writer);
    }

    public Question(Long id, String contents, String title, User writer) {
        this.id = id;
        this.contents = contents;
        this.title = title;
        this.writer = writer;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User getWriter() {
        return writer;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<DeleteHistory> deleteBy(User user) {
        validateDeletableBy(user);
        List<DeleteHistory> deleteHistories = deleteAnswersBy(user);
        deleteHistories.add(deleteQuestion());
        return deleteHistories;
    }

    private void validateDeletableBy(User user) {
        if (!isOwner(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        if (deleted) {
            throw new AlreadyDeletedException("이미 삭제된 질문입니다.");
        }
    }

    private List<DeleteHistory> deleteAnswersBy(User user) {
        return answers.stream()
                .map(it -> it.deleteBy(user))
                .collect(Collectors.toList());
    }

    private DeleteHistory deleteQuestion() {
        this.deleted = true;
        return DeleteHistory.ofQuestion(id, writer);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                ", title='" + title + '\'' +
                ", writerId=" + writer.getId() +
                ", answers=" + answers +
                '}';
    }
}
