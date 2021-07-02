package qna.domain.user;


public class TestUser {
    public static final String USER_ID = "testUserId";
    public static final String PASSWORD = "testPassword";
    public static final String NAME = "testName";
    public static final String EMAIL = "test@test.com";

    private static Long INCREASE_ID = 0L;

    public static User create() {
        //INCREASE_ID++;
        return new User(
                USER_ID + INCREASE_ID,
                PASSWORD + INCREASE_ID,
                NAME + INCREASE_ID,
                EMAIL + INCREASE_ID
        );
    }
}
