package qna.domain.content.question;

import qna.domain.content.Content;
import qna.domain.content.answer.Answer;
import qna.domain.log.DeleteHistory;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;
import qna.exception.ForbiddenException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Entity
public class Question extends Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String title;
    @Lob
    private String contents;
    @ManyToOne
    @JoinColumn(name = "WRITER_ID")
    private User writer;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @Column(nullable = false)
    private boolean deleted;

    protected Question() {
    }

    public Question(User writer, String title, String contents, List<Answer> answers) {
        this(null, writer, title, contents, answers);
    }

    public Question(Long id, User writer, String title, String contents, List<Answer> answers) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.answers = new ArrayList<>(answers);
        this.writer = writer;
    }

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        this.answers.add(answer);
    }

    public void removeAnswer(Answer answer) {
        answer.toDeleted();
        answers.remove(answer);
    }

    public List<DeleteHistory> deleteBy(User user, LocalDateTime timestamp) {
        validateOwner(user);
        validateContainsOtherUserAnswer(user);

        answers.forEach(Answer::toDeleted);
        this.deleted = true;

        return Collections.unmodifiableList(createDeleteHistories(user, timestamp));
    }

    private void validateOwner(User user) {
        if (!this.writer.equals(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void validateContainsOtherUserAnswer(User user) {
        boolean isContainsOtherUserAnswer = this.answers.stream()
                .anyMatch(answer -> !answer.isOwner(user));

        if (isContainsOtherUserAnswer) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private List<DeleteHistory> createDeleteHistories(User user, LocalDateTime timestamp) {
        List<DeleteHistory> deleteHistories = answers.stream()
                .map(content -> new DeleteHistory(content, user, timestamp))
                .collect(toList());
        deleteHistories.add(new DeleteHistory(this, user, timestamp));

        return deleteHistories;
    }

    public Long getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
