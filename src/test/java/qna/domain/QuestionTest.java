package qna.domain;

public class QuestionTest {
    public static final Question QUESTION_1 = new Question("title1", "contents1").writeBy(UserTest.USER_JAVAJIGI);
    public static final Question QUESTION_2 = new Question("title2", "contents2").writeBy(UserTest.USER_SANJIGI);
}
