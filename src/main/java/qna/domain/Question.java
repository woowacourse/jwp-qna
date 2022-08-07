package qna.domain;

import java.util.ArrayList;
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
import qna.CannotDeleteException;

@Entity
public class Question extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(name = "writer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Embedded
    private Answers answers;

    @Column(nullable = false)
    private boolean deleted = false;

    public Question(String title, String contents) {
        this(null, title, contents, new ArrayList<>());
    }

    public Question(Long id, String title, String contents) {
        this(id, title, contents, new ArrayList<>());
    }

    public Question(Long id, String title, String contents, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.answers = new Answers(answers);
    }

    protected Question() {
    }

    public Question writeBy(User writer) {
        this.writer = writer;
        return this;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.toQuestion(this);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<DeleteHistory> deleteBy(User user) {
        checkIsWrittenBy(user);

        List<DeleteHistory> deleteHistories = deleteAnswersBy(user);

        this.deleted = true;
        deleteHistories.add(DeleteHistory.from(this));

        return deleteHistories;
    }

    private List<DeleteHistory> deleteAnswersBy(final User user) {
        try {
            return answers.deleteAllBy(user);
        } catch (CannotDeleteException e) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private void checkIsWrittenBy(User user) {
        if (!writer.equals(user)) {
            throw new CannotDeleteException("질문은 작성자 본인만 삭제할 수 있습니다.");
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

    public User getWriter() {
        return writer;
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }
}
