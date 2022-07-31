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

    public List<DeleteHistory> delete() {
        this.deleted = true;
        List<DeleteHistory> deleteHistories = answers.stream()
                .map(Answer::delete)
                .collect(Collectors.toList());
        deleteHistories.add(DeleteHistory.of(this));
        return deleteHistories;
    }
}
