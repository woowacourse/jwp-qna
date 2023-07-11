package qna.fixtures;

import qna.domain.Question;

import static qna.fixtures.UserFixture.JAVAJIGI;
import static qna.fixtures.UserFixture.SANJIGI;

public class QuestionFixture {

    public static final Question Q1 = new Question("title1", "contents1").writeBy(JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(SANJIGI);

}
