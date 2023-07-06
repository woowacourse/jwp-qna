package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    private UserRepository users;

    @Test
    void save() {
        // when
        final User expected = users.save(JAVAJIGI);

        //then
        assertThat(users.findById(expected.getId()).get()).isEqualTo(expected);
    }

    @Test
    void find() {
        // given
        final User expected1 = users.save(JAVAJIGI);
        final User expected2 = users.save(SANJIGI);

        // when
        final User actual1 = users.findById(expected1.getId()).get();
        final User actual2 = users.findById(expected2.getId()).get();

        //then
        assertAll(
                () -> assertThat(actual1).isEqualTo(expected1),
                () -> assertThat(actual2).isEqualTo(expected2)
        );
    }

    @Test
    void update() {
        // given
        final User expected = users.save(JAVAJIGI);

        // when
        expected.setEmail("123@123");

        //then
        assertThat(users.findById(expected.getId()).get().getEmail()).isEqualTo("123@123");
    }

    @Test
    void delete() {
        // given
        final User expected = users.save(JAVAJIGI);

        // when
        users.delete(expected);

        //then
        assertThat(users.findById(expected.getId()).isPresent()).isFalse();
    }
}
