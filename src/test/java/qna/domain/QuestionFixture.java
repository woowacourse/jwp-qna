package qna.domain;

public class QuestionFixture {

    public static Question q1() {
        return new Question("title1", "contents1").writeBy(UserFixture.javajigi());
    }

    public static Question q2() {
        return new Question("title2", "contents2").writeBy(UserFixture.sanjigi());
    }
}
