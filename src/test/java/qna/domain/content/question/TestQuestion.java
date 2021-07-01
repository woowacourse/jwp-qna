package qna.domain.content.question;

import qna.domain.content.answer.Answer;
import qna.domain.user.User;

import java.util.List;

public class TestQuestion extends Question {
    private static Long INCREASE_ID = 0L;

    public static final String TITLE = "testTitle";
    public static final String CONTENTS = "TestContents";

    private TestQuestion(Long id,
                        User writer,
                        String title,
                        String contents,
                        List<Answer> answers) {
        super(id, writer, title, contents, answers);
    }

    public static TestQuestion create(User writer, List<Answer> answers) {
        INCREASE_ID++;
        return new TestQuestion(
                INCREASE_ID,
                writer,
                TITLE,
                CONTENTS,
                answers
        );
    }
}
