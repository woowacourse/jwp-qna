package qna.domain;

public class Fixture {

    public static User userFixtureJavajigi() {
        return new User("javajigi", "password", "name", "javajigi@slipp.net");
    }

    public static User userFixtureSanjigi() {
        return new User("sanjigi", "password2", "name2", "sanjigi@slipp.net");
    }

    public static Question questionFixture(User user) {
        return new Question("내용입니다", "제목입니다", user);
    }
}
