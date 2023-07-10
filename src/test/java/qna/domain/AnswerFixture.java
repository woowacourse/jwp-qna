package qna.domain;

public class AnswerFixture {

    public static Answer a1() {
        return new Answer(UserFixture.javajigi(), QuestionFixture.q1(), "Answers Contents1");
    }

    public static Answer a2() {
        return new Answer(UserFixture.sanjigi(), QuestionFixture.q2(), "Answers Contents1");
    }
}
