package qna.domain.question;

import java.util.ArrayList;
import java.util.List;
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
import qna.domain.DateHistory;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.user.User;
import qna.domain.answer.Answer;

@Table(name = "question")
@Entity
public class Question extends DateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contents;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false, length = 100)
    private String title;

    @JoinColumn(name = "answer_id")
    @OneToMany
    private List<Answer> answers = new ArrayList<>();

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

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
        this.answers.add(answer);
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<DeleteHistory> delete() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        this.deleted = true;
        deleteHistories.add(DeleteHistory.of(this));
        for (Answer answer : this.answers) {
            deleteHistories.add(answer.delete());
        }
        return deleteHistories;
    }
}
