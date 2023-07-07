package qna.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import qna.UnAuthorizedException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @DisplayName("업데이트 테스트")
    @Nested
    class Update {

        @DisplayName("사용자 정보를 업데이트 한다.")
        @Test
        void success() {
            // given
            User ethan = new User("ethan", "1234", "김석호", "test@test.com");
            User d2 = new User("D2", "1234", "박정훈", "test1@test.com");

            // when
            ethan.update(ethan, d2);

            // then
            assertAll(
                    () -> assertThat(ethan.getName()).isEqualTo(d2.getName()),
                    () -> assertThat(ethan.getEmail()).isEqualTo(d2.getEmail())
            );
        }

        @DisplayName("id가 다르면 정보를 업데이트 할 수 없다.")
        @Test
        void fail_unAuthorized_userId() {
            // given
            User ethan = new User("ethan", "1234", "김석호", "test@test.com");
            User d2 = new User("D2", "1234", "박정훈", "test1@test.com");

            // when, then
            assertThatThrownBy(() -> JAVAJIGI.update(ethan, d2))
                    .isInstanceOf(UnAuthorizedException.class);
        }

        @DisplayName("비밀번호가 다르면 정보를 업데이트 할 수 없다.")
        @Test
        void fail_unAuthorized_password() {
            // given
            User ethan = new User("ethan", "1234", "김석호", "test@test.com");
            User invalidPasswordEthan = new User("ethan", "12345", "김석호", "test@test.com");
            User d2 = new User("D2", "1234", "박정훈", "test1@test.com");

            // when, then
            assertThatThrownBy(() -> invalidPasswordEthan.update(ethan, d2))
                    .isInstanceOf(UnAuthorizedException.class);
        }
    }
}
