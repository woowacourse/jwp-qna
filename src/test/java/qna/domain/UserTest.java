package qna.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.UnAuthorizedException;
import qna.fixture.UserFixture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    @DisplayName("사용자 정보를 업데이트 한다.")
    void updateUserInfo() {
        // given
        final User original = UserFixture.JAVAJIGI();
        final User loginUser = UserFixture.JAVAJIGI();
        final User target = new User("userId", "password", "javajigi", "javajigi@woowa.course");

        // when
        original.update(loginUser, target);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(original.getName()).isEqualTo(target.getName());
            softly.assertThat(original.getEmail()).isEqualTo(target.getEmail());
        });
    }

    @Test
    @DisplayName("로그인 정보가 다르면 업데이트시 예외가 발생한다.")
    void failUpdateWhenLoginUserIsNotMatched() {
        // given
        final User original = UserFixture.JAVAJIGI();
        final User loginUser = UserFixture.SANJIGI();
        final User target = new User("userId", "password", "javajigi", "javajigi@woowa.course");

        // when & then
        assertThatThrownBy(() -> original.update(loginUser, target))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("바꿀 사용자의 비밀번호가 다르면 예외가 발생한다.")
    void failUpdateWhenTargetPasswordIsNotMatched() {
        // given
        final User original = UserFixture.JAVAJIGI();
        final User loginUser = UserFixture.JAVAJIGI();
        final User target = new User("userId", "wrongPassword", "javajigi", "javajigi@woowa.course");

        // when & then
        assertThatThrownBy(() -> original.update(loginUser, target))
                .isInstanceOf(UnAuthorizedException.class);
    }
}
