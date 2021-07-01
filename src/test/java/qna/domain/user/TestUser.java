package qna.domain.user;

public class TestUser extends User {
    public static final String USER_ID = "testUserId";
    public static final String PASSWORD = "testPassword";
    public static final String NAME = "testName";
    public static final String EMAIL = "test@test.com";

    private static Long INCREASE_ID = 0L;

    private TestUser(Long id, String userId, String password, String name, String email) {
        super(id, userId, password, name, email);
    }

    public static TestUser create() {
        INCREASE_ID++;
        return new TestUser(
                INCREASE_ID,
                USER_ID + INCREASE_ID,
                PASSWORD + INCREASE_ID,
                NAME + INCREASE_ID,
                EMAIL + INCREASE_ID
        );
    }
}
