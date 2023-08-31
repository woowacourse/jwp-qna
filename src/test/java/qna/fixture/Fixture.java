package qna.fixture;

import qna.domain.*;

public class Fixture {

    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");
    public static final Question Q1 = new Question("title1", JAVAJIGI, "contents1");

}
