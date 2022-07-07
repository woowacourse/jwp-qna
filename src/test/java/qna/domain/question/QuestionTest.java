package qna.domain.question;

import qna.domain.answer.AnswerTest;
import qna.domain.user.UserTest;

public class QuestionTest {

    public static final Question Q1 = new Question("title1", "contents1", UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2", UserTest.SANJIGI);

    static {
        Q1.addAnswer(AnswerTest.A1);
        Q1.addAnswer(AnswerTest.A2);
    }
}
