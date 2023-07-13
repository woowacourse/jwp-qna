package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
class UserRepositoryTest {

    private User javajigi;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        javajigi = new User(
                "javajigi",
                "password",
                "name",
                "javajigi@slipp.net"
        );
    }

    @Test
    void 유저가_저장된_후_ID가_초기화_된다(){
        User user = javajigi;

        User save = userRepository.save(user);

        assertThat(save.getId()).isNotNull();
    }

    @Test
    void 저장_전_후_엔티티_래퍼런스는_동일하다(){
        User user = javajigi;

        User save = userRepository.save(user);

        assertThat(user).isEqualTo(save);
    }

    @Test
    void 유저의_변경사항이_자동으로_반영된다(){
        User user = javajigi;
        userRepository.save(user);

        user.setUserId("떙칠");

        final Optional<User> found = userRepository.findByUserId("떙칠");
        assertThat(found).isNotEmpty();
    }

    @Test
    void 유저의_PK값이_아닌_컬럼으로_엔티티를_조회해도_같은_인스턴스를_반환한다(){
        User user = javajigi;
        // INSERT 수행
        userRepository.save(user);
        System.out.println("====================select");
        //user.setUserId("떙칠");
        // findByUserId 메서드를 통해서 UPDATE 수행 후 SELECT 수행이 되었다. [사실]
        // 인스턴스를 1차 캐시에서 가져오는데 그런다면 왜 SELECT 을 수행하는지?
        // [가설] 떙칠의 ID를 알기 위해서 SELECT 를 사용한다.
//        final Optional<User> found = userRepository.findByUserId("떙칠");
//        assertThat(found.get()).isEqualTo(user);
    }

    @Test
    void 데이터베이스에서_조회한_내용과_1차_캐시에_저장된_내용이_다르면_인스턴스가_다른가(){
        User user = javajigi;
        userRepository.save(user);

        String sql = "UPDATE USER SET EMAIL = 'A' WHERE ID = " + user.getId();
        final int update = jdbcTemplate.update(sql);
        assertThat(update).isOne();


//        [사실] 1차 캐시에 있는 값(email)과 데이터베이스에 있는 값이 다르다.
//        JPA를 사용해서 조회했을 때 최신 값을 반영하지 않고, JPA으 1차 캐시값을 반환하였다.
//         만약 다수의 웹 어플리케이션 서버를 운용하는 환경이라면 충분히 같은 문제가 발생할 것 같은데
//         이것에 대한 대안이 없는지?
        final Optional<User> found = userRepository.findByUserId("javajigi");
        assertThat(found.get()).isEqualTo(user);
        //assertThat(found.get().getEmail()).isEqualTo("A");
    }

    @Test
    void 유저가_저장되면_생성_시간이_기록된다() {
        final User user = javajigi;

        userRepository.save(user);

        assertThat(user.getTimeLog().getCreatedAt()).isNotNull();
    }

    @Test
    void 유저의_정보가_변경되면_변경_시간이_최신화_된다() {
        final User user = javajigi;
        userRepository.save(user);
        final LocalDateTime originalLocalDateTime = LocalDateTime.from(user.getTimeLog().getUpdatedAt());

        user.setUserId("화이트캣");

        final User updatedUser = userRepository.findByUserId("화이트캣").get();
        assertThat(updatedUser.getTimeLog().getUpdatedAt()).isAfter(originalLocalDateTime);
    }
}
