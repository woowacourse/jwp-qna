package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.fixtures.UserFixture.JAVAJIGI;

@DataJpaTest
public class UserTest {


    @DisplayName("기존 사용자의 name과 email을 수정할 수 있다.")
    @Test
    void update_success() {
        // given
        final User loginUser = new User("javajigi", JAVAJIGI.getPassword(), JAVAJIGI.getName(), JAVAJIGI.getEmail());
        final User target = new User(1L, "javajigi", "password", "updatedName", "update@slipp.net");

        // when
        JAVAJIGI.update(loginUser, target);

        // then
        assertAll(
                () -> assertThat(JAVAJIGI.getName()).isEqualTo("updatedName"),
                () -> assertThat(JAVAJIGI.getEmail()).isEqualTo("update@slipp.net")
        );
    }

    @DisplayName("로그인된 사용자의 userId가 다르면 예외가 발생한다.")
    @Test
    void update_fail_userId() {
        // given
        final User loginUser = new User("javajigiiiii", JAVAJIGI.getPassword(), JAVAJIGI.getName(), JAVAJIGI.getEmail());
        final User target = new User(1L, JAVAJIGI.getUserId(), JAVAJIGI.getPassword(), "updatedName", "update@slipp.net");

        // when, then
        assertThatThrownBy(() -> JAVAJIGI.update(loginUser, target))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("바꾸려고 하는 사용자의 password가 다르면 예외가 발생한다.")
    @Test
    void update_fail_password() {
        // given
        final User loginUser = new User(JAVAJIGI.getUserId(), JAVAJIGI.getPassword(), JAVAJIGI.getName(), JAVAJIGI.getEmail());
        final User target = new User(1L, JAVAJIGI.getUserId(), "password!!", "updatedName", "update@slipp.net");

        // when, then
        assertThatThrownBy(() -> JAVAJIGI.update(loginUser, target))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("대상 유저의 이름과 이메일이 같으면 true를 반환한다.")
    @Test
    void equalsNameAndEmail_success() {
        // given
        final User target = new User(1L, JAVAJIGI.getUserId(), JAVAJIGI.getPassword(), JAVAJIGI.getName(), JAVAJIGI.getEmail());

        // when, then
        assertThat(JAVAJIGI.equalsNameAndEmail(target)).isTrue();
    }

    @DisplayName("대상 유저의 이름과 이메일이 다르면 false를 반환한다.")
    @Test
    void equalsNameAndEmail_fail() {
        // given
        final User target1 = new User(1L, JAVAJIGI.getUserId(), JAVAJIGI.getPassword(), "hubcreator", JAVAJIGI.getEmail());
        final User target2 = new User(1L, JAVAJIGI.getUserId(), JAVAJIGI.getPassword(), JAVAJIGI.getName(), "hubcreator@naver.com");

        // when, then
        assertAll(
                () -> assertThat(JAVAJIGI.equalsNameAndEmail(target1)).isFalse(),
                () -> assertThat(JAVAJIGI.equalsNameAndEmail(target2)).isFalse()
        );
    }

    @DisplayName("게스트 유저인지 확인할 수 있다.")
    @Test
    void isGuestUser() {
        // given
        final User guestUser = User.GUEST_USER;

        // when, then
        assertAll(
                () -> assertThat(guestUser.isGuestUser()).isTrue(),
                () -> assertThat(JAVAJIGI.isGuestUser()).isFalse()
        );
    }
}
