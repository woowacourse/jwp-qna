package qna.domain.user;

public class GuestUser extends User {
    private static final String GUEST = "guest";

    private GuestUser() {
        super(GUEST, GUEST, GUEST, GUEST);
    }

    public static GuestUser create() {
        return new GuestUser();
    }

    @Override
    public void update(User loginUser, User target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGuestUser() {
        return true;
    }
}
