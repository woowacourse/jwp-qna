package qna.domain.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    @DisplayName("user의 정보를 변경한다.")
    void update() {
        User user = new User("dusdn1702", "123", "yeonwoo", "dusdn1702@gmail.com");

        User another = new User("dusdn1702", "123", "cho", "sally1702@gmial.com");

        user.update(user, another);

        assertThat(user.getEmail()).isEqualTo(another.getEmail());
        assertThat(user.getName()).isEqualTo(another.getName());
    }
}
