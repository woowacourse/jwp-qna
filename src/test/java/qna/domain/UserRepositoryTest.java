package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaConfig.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("JAVAJIGI와 같이 객체 안에 id 값이 존재한다면, 해당 id를 가진 유저가 있는지 먼저 select문을 보내고, id 값을 null로 만들고 insert 쿼리를 날린다.")
    @Test
    void save() {
        User savedUser = userRepository.save(JAVAJIGI);

        assertThat(savedUser).isNotEqualTo(JAVAJIGI);
    }

    @DisplayName("userId 값으로 데이터를 찾기 때문에 select 쿼리를 보낸다.")
    @Test
    void findByUserId() {
        User user = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(user);

        Optional<User> byUserId = userRepository.findByUserId(savedUser.getUserId());

        assertThat(byUserId.get()).isEqualTo(savedUser);
    }
}
