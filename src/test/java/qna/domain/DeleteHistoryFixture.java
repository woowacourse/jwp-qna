package qna.domain;

import java.time.LocalDateTime;

public class DeleteHistoryFixture {

    public static DeleteHistory d1() {
        return new DeleteHistory(ContentType.ANSWER, 1L, 2L, LocalDateTime.now());
    }

    public static DeleteHistory d2() {
        return new DeleteHistory(ContentType.QUESTION, 3L, 4L, LocalDateTime.now());
    }
}
