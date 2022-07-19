package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository users;

    private static User javajigi;

    @BeforeEach
    void setUp() {
        javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
    }

    @DisplayName("사용자 정보 저장")
    @Test
    void save() {
        User actual = users.save(javajigi);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(javajigi);
    }

    @DisplayName("사용자 정보 조회")
    @Test
    void findById() {
        User expected = users.save(javajigi);
        synchronize();

        Optional<User> actual = users.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("사용자 아이디를 이용하여 사용자 정보 조회")
    @Test
    void findByUserId() {
        User expected = users.save(javajigi);
        synchronize();

        Optional<User> actual = users.findByUserId(expected.getUserId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("사용자 정보 수정")
    @Test
    void update() {
        User user = users.save(javajigi);
        User expected = new User("javajigi", "password", "update", "update@slipp.net");

        user.update(javajigi, expected);
        synchronize();

        Optional<User> actual = users.findById(user.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("createDate")
                .ignoringFields("updateDate")
                .isEqualTo(expected);
    }

    @DisplayName("사용자 정보 삭제")
    @Test
    void delete() {
        User user = users.save(javajigi);
        synchronize();

        users.delete(user);
        synchronize();

        assertThat(users.findAll()).hasSize(0);
    }
}
