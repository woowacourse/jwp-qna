package qna.domain.content.answer;

import qna.domain.user.User;

public class TestAnswer extends Answer {
    public static final String CONTENTS = "testContents";
    private static Long INCREASE_ID = 0L;

    private TestAnswer(Long id, User writer, String contents) {
        super(id, writer, contents);
    }

    public static TestAnswer create(User writer) {
        INCREASE_ID++;
        return new TestAnswer(
                INCREASE_ID,
                writer,
                CONTENTS
        );
    }
}
