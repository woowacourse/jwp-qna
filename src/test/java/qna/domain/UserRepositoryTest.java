package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @DisplayName("User를 DB에 저장한다.")
    @Test
    void save() {
        User expected = new User("javajigi", "password", "name", "javajigi@slipp.net");
        User actual = users.save(expected);

        // 영속화가 되지 않는 엔티티
        // JPA entity save -> merge persist

        assertAll(() -> {
            assertThat(actual).isSameAs(expected);
            assertThat(actual.getCreatedAt()).isNotNull();
            assertThat(actual.getName()).isNotNull();
            assertThat(actual.getPassword()).isNotNull();
            assertThat(actual.getUserId()).isNotNull();
        });
    }

    @DisplayName("UserId가 중복인 User를 저장하면 예외를 발생한다.")
    @Test
    void saveDuplicateUserID() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        users.save(user);
        User duplicateUserId = new User("javajigi", "password2", "name2", "");

        assertThatThrownBy(() -> users.save(duplicateUserId))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("User에서 변경감지가 작동하지 않는 것을 확인한다.")
    @Test
    void isDirtyChecking() {
        User expected = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User actual = users.save(expected);

        expected.setName("newName");

        assertThat(actual.getName()).isEqualTo("name");
        assertThat(expected.getName()).isEqualTo("newName");
    }
}
