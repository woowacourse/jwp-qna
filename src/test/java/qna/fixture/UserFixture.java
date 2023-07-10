package qna.fixture;

import qna.domain.User;

public class UserFixture {
    public static User JAVAJIGI() {
        return new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    }

    public static User SANJIGI() {
        return new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");
    }

    public static User SEONGHA() {
        return new User("seongha", "password", "name", "seongha@slipp.net");
    }
}
