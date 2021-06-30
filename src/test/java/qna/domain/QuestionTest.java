package qna.domain;

import qna.domain.content.question.Question;

import java.util.Collections;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1", Collections.emptyList(), UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2", Collections.emptyList(), UserTest.SANJIGI);
}
