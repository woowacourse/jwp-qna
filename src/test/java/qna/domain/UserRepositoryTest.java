package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    private static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");

    @DisplayName("사용자 정보 저장")
    @Test
    void save() {
        User actual = users.save(JAVAJIGI);

        assertAll(
                () -> assertThat(actual.getName()).isEqualTo(JAVAJIGI.getName()),
                () -> assertThat(actual.getEmail()).isEqualTo(JAVAJIGI.getEmail())
        );
    }

    @DisplayName("사용자 정보 조회")
    @Test
    void findById() {
        User expected = users.save(JAVAJIGI);

        Optional<User> actual = users.findById(expected.getId());

        assertThat(actual).hasValue(expected);
    }

    @DisplayName("사용자 아이디를 이용하여 사용자 정보 조회")
    @Test
    void findByUserId() {
        User expected = users.save(JAVAJIGI);

        Optional<User> actual = users.findByUserId(expected.getUserId());

        assertThat(actual).hasValue(expected);
    }

    @DisplayName("사용자 정보 수정")
    @Test
    void update() {
        User user = users.save(JAVAJIGI);
        User expected = new User(1L, "javajigi", "password", "update", "update@slipp.net");

        user.update(JAVAJIGI, expected);
        Optional<User> actual = users.findById(user.getId());

        assertAll(
                () -> assertThat(actual.get().getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.get().getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @DisplayName("사용자 정보 삭제")
    @Test
    void delete() {
        User user = users.save(JAVAJIGI);

        users.delete(user);

        assertThat(users.findAll()).hasSize(0);
    }
}
