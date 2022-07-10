package qna.domain.question;

import qna.domain.user.UserTest;

public class QuestionTest {
    public static final Question Q1 = new Question(1L, "title1", "contents1", UserTest.JAVAJIGI);
    public static final Question Q2 = new Question(2L, "title2", "contents2", UserTest.SANJIGI);
}
