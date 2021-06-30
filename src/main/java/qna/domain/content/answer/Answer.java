package qna.domain.content.answer;

import qna.domain.content.question.Question;
import qna.domain.user.User;
import qna.exception.NotFoundException;
import qna.exception.UnAuthorizedException;

import java.util.Objects;

public class Answer {
    private Long id;
    private User writer;
    private Question question;
    private String contents;
    private boolean deleted;

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

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public void toDeleted() {
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public Long getWriterId() {
        return writer.getId();
    }

    public Long getQuestionId() {
        return question.getId();
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
