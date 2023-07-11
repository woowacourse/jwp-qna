package qna.fixture;

import static qna.fixture.QuestionFixture.Q1;
import static qna.fixture.UserFixture.JAVAJIGI;
import static qna.fixture.UserFixture.SANJIGI;

import qna.domain.Answer;

public final class AnswerFixture {

    public static final Answer A1 = new Answer(JAVAJIGI, Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(SANJIGI, Q1, "Answers Contents2");

    private AnswerFixture() {
    }
}
