package qna.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends MappedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String contents;
    @ManyToOne
    private User writer;
    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "question")
    private final List<Answer> answers = new ArrayList<>();

    public Question(String title, String contents) {
        this(null, title, contents);
    }

    protected Question() {
    }

    public Question(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Question writeBy(User writer) {
        this.writer = writer;
        writer.addQuestion(this);
        return this;
    }

    public boolean isOwner(User writer) {
        return this.writer.getId().equals(writer.getId());
    }

    public void addAnswer(Answer answer) {
        if (!this.answers.contains(answer)) {
            answers.add(answer);
        }
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

    public Long getWriterId() {
        return writer.getId();
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writerId=" + writer.getId() +
                ", deleted=" + deleted +
                '}';
    }

    public void deleteAnswer(Answer answer) {
        this.answers.remove(answer);
    }
}
