package qna.domain;

public class UserFixture {

    public static User javajigi() {
        return new User("javajigi", "password", "name", "javajigi@slipp.net");
    }

    public static User sanjigi() {
        return new User("sanjigi", "password", "name", "sanjigi@slipp.net");
    }
}
