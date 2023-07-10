package qna.domain;

public class AnswerTest {
    private final QuestionTest questionTest;

    public AnswerTest(final QuestionTest questionTest) {
        this.questionTest = questionTest;
    }

    public Answer makeAnswer1() {
        return new Answer(UserTest.JAVAJIGI, questionTest.Q1, "Answers Contents1");
    }

    public Answer makeAnswer2() {
        return new Answer(UserTest.SANJIGI, questionTest.Q1, "Answers Contents2");
    }
}
