package qna.fixture;

import qna.domain.User;

public final class UserFixture {

    public static final User JAVAJIGI = new User(
            1L,
            "javajigi",
            "password",
            "name",
            "javajigi@slipp.net"
    );
    public static final User SANJIGI = new User(
            2L,
            "sanjigi",
            "password",
            "name",
            "sanjigi@slipp.net"
    );

    private UserFixture() {
    }
}
