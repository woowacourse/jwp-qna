package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User(null, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(null, "sanjigi", "password", "name", "sanjigi@slipp.net");

    UserRepository users;

    UserTest(UserRepository users) {
        this.users = users;
    }

    @Test
    void findByUserId() {
        User saved = users.save(JAVAJIGI);
        users.flush();

        Optional<User> maybe = users.findByUserId(JAVAJIGI.getUserId());

        assertThat(maybe).get().isEqualTo(saved);
    }

    @Test
    void save() {
        User saved = users.save(JAVAJIGI);

        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getUserId()).isEqualTo(JAVAJIGI.getUserId())
        );
    }

    @Test
    void findById() {
        User saved = users.save(JAVAJIGI);

        User expected = users.findById(saved.getId()).get();

        assertThat(expected).isEqualTo(saved);
    }

    @Test
    void saveToUpdate() {
        User saved = users.save(JAVAJIGI);
        users.flush();

        User user = users.findById(saved.getId()).get();
        user.setUserId("수정된 내용");
        User updated = users.save(saved);
        users.flush();

        User exptected = users.findById(updated.getId()).get();
        assertThat(exptected.getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    void delete() {
        User saved = users.save(JAVAJIGI);
        users.flush();

        users.delete(saved);
        users.flush();

        Optional<User> maybe = users.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
