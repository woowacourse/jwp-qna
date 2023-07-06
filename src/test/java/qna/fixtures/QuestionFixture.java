package qna.fixtures;

import qna.domain.Question;

public class QuestionFixture {
    public static final Question Q1 = new Question(1L, "title1", "contents1").writeBy(UserFixture.JAVAJIGI);
    public static final Question Q2 = new Question(2L, "title2", "contents2").writeBy(UserFixture.SANJIGI);
}
