package qna.domain;

import java.time.LocalDateTime;

public class DeleteHistoryFixture {
    public static final DeleteHistory D1 = new DeleteHistory(ContentType.ANSWER, 1L, 2L, LocalDateTime.now());
    public static final DeleteHistory D2 = new DeleteHistory(ContentType.QUESTION, 3L, 4L, LocalDateTime.now());
}
