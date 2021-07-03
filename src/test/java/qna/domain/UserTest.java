package qna.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User("javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User("sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("User 객체를 jpa를 이용해 저장한다.")
    void persist() {
        entityManager.persist(JAVAJIGI);
        assertThat(JAVAJIGI).isEqualTo(entityManager.find(User.class, JAVAJIGI.getId()));
    }
}
