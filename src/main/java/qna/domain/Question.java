package qna.domain;

import qna.CannotDeleteException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Question extends TimeStampEntity {
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

    @Embedded
    private final Answers answers = new Answers(new ArrayList<>());

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
        if (writer == null) {
            throw new UnAuthorizedException();
        }
        if (this.writer == null) {
            this.writer = writer;
            writer.addQuestion(this);
        }
        return this;
    }

    public boolean isOwner(User writer) {
        return this.writer.getId().equals(writer.getId());
    }

    public void addAnswer(Answer answer) {
        answers.addAnswer(answer);
    }

    public Long getId() {
        return id;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public List<Answer> getAnswers() {
        return answers.getAnswers();
    }

    public DeleteHistories delete(User loginUser, Answers answers) throws CannotDeleteException {
        validateAlreadyDeleted();
        validateUserMatch(loginUser);
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, this.getId(), loginUser));
        deleteHistories.addAll(answers.delete(loginUser));
        this.deleted = true;
        return new DeleteHistories(deleteHistories);
    }

    private void validateUserMatch(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void validateAlreadyDeleted() throws CannotDeleteException {
        if (isDeleted()) {
            throw new CannotDeleteException("이미 삭제한 질문입니다.");
        }
    }

    public User getWriter() {
        return writer;
    }

    public boolean isDeleted() {
        return deleted;
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
}
