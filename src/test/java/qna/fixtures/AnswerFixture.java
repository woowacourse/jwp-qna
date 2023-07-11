package qna.fixtures;

import qna.domain.Answer;

public class AnswerFixture {
    public static final Answer A1 = new Answer(1L, UserFixture.JAVAJIGI, QuestionFixture.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(2L, UserFixture.SANJIGI, QuestionFixture.Q1, "Answers Contents2");
    public static final Answer A3 = new Answer(3L, UserFixture.SANJIGI, QuestionFixture.Q2, "Answers Contents2");
}
