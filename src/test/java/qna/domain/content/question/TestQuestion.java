package qna.domain.content.question;

import qna.domain.content.answer.Answer;
import qna.domain.user.User;

import java.util.Collections;
import java.util.List;

public class TestQuestion {
    public static final String TITLE = "testTitle";
    public static final String CONTENTS = "TestContents";

    private static Long INCREASE_ID = 0L;

    public static Question create(User writer) {
        return new Question(
                writer,
                TITLE,
                CONTENTS,
                Collections.emptyList()
        );
    }

    public static Question createWithId(User writer, List<Answer> answers) {
        INCREASE_ID++;
        return new Question(
                INCREASE_ID,
                writer,
                TITLE,
                CONTENTS,
                answers
        );
    }
}
