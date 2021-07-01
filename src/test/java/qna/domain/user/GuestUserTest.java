package qna.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class GuestUserTest {

    private GuestUser guestUser;

    @BeforeEach
    void setUp() {
        guestUser = GuestUser.create();
    }

    @Test
    void update() {
        assertThatThrownBy(
                () -> guestUser.update(guestUser, guestUser)
        ).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void isGuestUser() {
        assertThat(guestUser.isGuestUser()).isTrue();
    }
}