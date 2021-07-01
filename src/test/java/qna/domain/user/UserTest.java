package qna.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.exception.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = TestUser.create();
    }

    @DisplayName("유저 정보를 변경한다.")
    @Test
    void update() {
        String UPDATE_NAME = "neozal";
        String UPDATE_EMAIL = "neozal@test.com";
        User updateUser = new User(
                user.getId(),
                user.getUserId(),
                user.getPassword(),
                UPDATE_NAME,
                UPDATE_EMAIL
        );

        user.update(user, updateUser);

        assertAll(
                () -> assertThat(user.getName()).isEqualTo(UPDATE_NAME),
                () -> assertThat(user.getEmail()).isEqualTo(UPDATE_EMAIL)
        );
    }

    @DisplayName("다른 유저의 정보는 변경할 수 없다.")
    @Test
    void update_() {
        String UPDATE_NAME = "neozal";
        String UPDATE_EMAIL = "neozal@test.com";
        User otherUser = new User(
                "testUserId",
                "testPassword",
                "testName",
                "test@test.com"
        );

        User updateUser = new User(
                user.getId(),
                user.getUserId(),
                user.getPassword(),
                UPDATE_NAME,
                UPDATE_EMAIL
        );

        assertThatThrownBy(
                () -> user.update(otherUser, updateUser)
        ).isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("비밀번호가 다르면 유저 정보를 변경할 수 없다.")
    @Test
    void update_occurExceptionWhenPasswordNotMatched() {
        final String UPDATE_NAME = "neozal";
        final String UPDATE_EMAIL = "neozal@test.com";
        final String OTHER_PASSWORD = "otherPassword";

        User updateUser = new User(
                user.getId(),
                user.getUserId(),
                OTHER_PASSWORD,
                UPDATE_NAME,
                UPDATE_EMAIL
        );

        assertThatThrownBy(
                () -> user.update(user, updateUser)
        ).isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void getId() {
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void getUserId() {
        assertThat(user.getUserId()).isNotNull();
    }

    @Test
    void getName() {
        assertThat(user.getName()).isNotNull();
    }

    @Test
    void getPassword() {
        assertThat(user.getPassword()).isNotNull();
    }

    @Test
    void getEmail() {
        assertThat(user.getEmail()).isNotNull();
    }

    @Test
    void isGuestUser() {
        assertThat(user.isGuestUser()).isFalse();
        assertThat(GuestUser.create().isGuestUser()).isTrue();
    }
}