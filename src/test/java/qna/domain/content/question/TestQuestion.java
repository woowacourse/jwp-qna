package qna.domain.content.question;

import qna.domain.content.answer.Answer;
import qna.domain.user.User;

import java.util.List;

public class TestQuestion {
    private static Long INCREASE_ID = 0L;

    public static final String TITLE = "testTitle";
    public static final String CONTENTS = "TestContents";

    public static Question create(User writer, List<Answer> answers) {
        return new Question(
                writer,
                TITLE,
                CONTENTS,
                answers
        );
    }
}
