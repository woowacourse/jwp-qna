package qna.domain.support;

import qna.domain.User;

public class UserFixture {

    private static final String HUNI_USER_ID = "huni";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String HUNI_EMAIL = "huni@naver.com";

    private UserFixture() {

    }

    public static User huni() {
        return TestUser.builder()
                .userId(HUNI_USER_ID)
                .name(NAME)
                .password(PASSWORD)
                .email(HUNI_EMAIL)
                .build();
    }
}
