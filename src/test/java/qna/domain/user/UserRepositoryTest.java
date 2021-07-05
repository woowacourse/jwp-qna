package qna.domain.user;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("user를 저장한다.")
    void save() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");

        users.save(user);

        assertThat(users.findById(user.getId())).isEqualTo(Optional.of(user));
    }

    @Test
    @DisplayName("user의 정보를 변경한다.")
    void update() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        users.save(user);

        User another = new User("dusdn1702", "sally", "cho", "sally@gmail.com");
        user.update(user, another);

        assertThat(user.getName()).isEqualTo(another.getName());
        assertThat(user.getEmail()).isEqualTo(another.getEmail());
    }
}