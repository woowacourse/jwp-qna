package qna.fixture;

import qna.domain.Answer;

public class AnswerFixture {
    public static Answer A1() {
        return new Answer(UserFixture.JAVAJIGI(), QuestionFixture.Q1(), "Answers Contents1");
    }

    public static Answer A2() {
        return new Answer(UserFixture.SANJIGI(), QuestionFixture.Q1(), "Answers Contents2");
    }
}
