package qna.domain.content.question;

import qna.domain.content.answer.Answer;
import qna.domain.log.DeleteHistory;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Question {
    private Long id;
    private String title;
    private String contents;
    private User writer;
    private List<Answer> answers;
    private boolean deleted;

    public Question(String title, String contents, List<Answer> answers, User writer) {
        this(null, title, contents, answers, writer);
    }

    public Question(Long id, String title, String contents, List<Answer> answers, User writer) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.answers = new ArrayList<>(answers);
        this.writer = writer;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public List<DeleteHistory> deleteBy(User user) {
        validateOwner(user);
        validateContainsOtherUserAnswer(user);

        answers.forEach(Answer::toDeleted);
        this.deleted = true;

        return Collections.unmodifiableList(createDeleteHistories(user));
    }

    private List<DeleteHistory> createDeleteHistories(User user) {
        List<DeleteHistory> deleteHistories = answers.stream()
                .map(content -> new DeleteHistory(content, user, LocalDateTime.now()))
                .collect(toList());
        deleteHistories.add(new DeleteHistory(this, user, LocalDateTime.now()));

        return deleteHistories;
    }

    private void validateOwner(User user) {
        if (!this.writer.equals(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void validateContainsOtherUserAnswer(User user) {
        boolean isContainsOtherUserAnswer = this.answers.stream()
                .map(Answer::getWriterId)
                .anyMatch(writerId -> !writerId.equals(user.getId()));

        if (isContainsOtherUserAnswer) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
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
        return Collections.unmodifiableList(answers);
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
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
