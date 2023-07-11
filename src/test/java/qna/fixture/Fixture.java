package qna.fixture;

import qna.domain.Answer;
import qna.domain.ContentType;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.QuestionTest;
import qna.domain.User;
import qna.domain.UserTest;

public class Fixture {

    public static final User JAVAJIGI = new User( "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User( "sanjigi", "password", "name", "sanjigi@slipp.net");

    public static final Question Q1 = new Question("title1", "contents1").writeBy(JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(SANJIGI);

    public static final Answer A1 = new Answer(JAVAJIGI, Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(SANJIGI, Q1, "Answers Contents2");
}
