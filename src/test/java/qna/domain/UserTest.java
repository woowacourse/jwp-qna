package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.UnAuthorizedException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserTest {

    @Test
    void 사용자의_계정Id가_일치하지_않으면_예외를_발생시킨다() {
        // given
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User loginUser = new User(1L, "kkulbul", "password", "name", "javajigi@slipp.net");
        User targetUser = new User("javajigi", "password", "kkulbul", "kkulbul@slipp.net");

        // expect
        assertThatThrownBy(() -> user.update(loginUser, targetUser))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void 사용자의_비밀번호가_일치하지_않으면_예외를_발생시킨다() {
        // given
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User loginUser = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User targetUser = new User("javajigi", "secret", "kkulbul", "kkulbul@slipp.net");

        // expect
        assertThatThrownBy(() -> user.update(loginUser, targetUser))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void 사용자의_이름과_이메일을_수정한다() {
        // given
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User loginUser = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User targetUser = new User("javajigi", "password", "kkulbul", "kkulbul@slipp.net");

        // when
        user.update(loginUser, targetUser);

        // then
        assertSoftly(softly -> {
            softly.assertThat(user.getName()).isEqualTo("kkulbul");
            softly.assertThat(user.getEmail()).isEqualTo("kkulbul@slipp.net");
        });
    }

    @Test
    void 이름과_이메일이_같은지_확인한다() {
        // given
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User target = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");

        // expect
        assertThat(user.equalsNameAndEmail(target)).isTrue();
    }
}
