package qna.domain;

import java.time.LocalDateTime;

public class DeleteHistoryFixture {
    public static final DeleteHistory D1 = new DeleteHistory(
            ContentType.ANSWER,
            AnswerFixture.A1.getId(),
            UserFixture.JAVAJIGI.getId(),
            LocalDateTime.now());
}
