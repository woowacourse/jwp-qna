package qna.domain.content.answer;

import qna.domain.user.User;
import qna.exception.UnAuthorizedException;

import java.util.Objects;

public class Answer {
    private Long id;
    private User writer;
    private String contents;
    private boolean deleted;

    public Answer(User writer, String contents) {
        this(null, writer, contents);
    }

    public Answer(Long id, User writer, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        this.writer = writer;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void toDeleted() {
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
