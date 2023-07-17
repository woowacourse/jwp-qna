package qna.domain;

public class QuestionTest {

    public final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);

    public final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);
}
