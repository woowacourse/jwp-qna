package qna.domain.content.answer;

import qna.domain.user.User;

public class TestAnswer {
    public static final String CONTENTS = "testContents";
    private static Long INCREASE_ID = 0L;

    public static Answer create(User writer) {
        return new Answer(
                writer,
                CONTENTS
        );
    }
}
