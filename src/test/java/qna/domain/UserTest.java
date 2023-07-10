package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class UserTest {

    public static final User JAVAJIGI = new User(
            "javajigi",
            "password",
            "name",
            "javajigi@slipp.net"

    );
    public static final User SANJIGI = new User(
            "sanjigi",
            "password",
            "name",
            "sanjigi@slipp.net"
    );

    @Test
    void 유저를_생성해도_생성시간은_채워지지않는다() {
        final User user = JAVAJIGI;

        assertThat(user.getTimeLog().getCreatedAt()).isNull();
    }
}
