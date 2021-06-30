package qna.domain.log;

import qna.domain.content.ContentType;
import qna.domain.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class DeleteHistory {
    private Long id;
    private ContentType contentType;
    private Long contentId;
    private User deleteUser;
    private LocalDateTime createDate;

    public DeleteHistory(ContentType contentType, Long contentId, User deleteUser, LocalDateTime createDate) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deleteUser = deleteUser;
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
