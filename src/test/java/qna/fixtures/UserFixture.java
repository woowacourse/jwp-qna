package qna.fixtures;

import qna.domain.User;

public enum UserFixture {

    JAVAJIGI("javajigi", "password", "자바지기", "javajigi@slipp.net"),
    SANJIGI("sanjigi", "password", "산지기", "sanjigi@slipp.net"),
    ;

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    UserFixture(final String userId, final String password, final String name, final String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User generate() {
        return generate(null);
    }

    public User generate(final Long id) {
        return new User(id, this.userId, this.password, this.name, this.email);
    }
}
