package qna.fixture;

import qna.domain.Question;

public class QuestionFixture {
    public static Question Q1() {
        return new Question("title1", "contents1").writeBy(UserFixture.JAVAJIGI());
    }

    public static Question Q2() {
        return new Question("title2", "contents2").writeBy(UserFixture.SANJIGI());
    }
}
