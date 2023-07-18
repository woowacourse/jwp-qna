package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.util.ReflectionTestUtils;
import qna.config.JpaConfig;

@DataJpaTest
@Import(JpaConfig.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    void ID_가_있는_필드_저장_시_Select_쿼리가_추가로_발생하며_auditing_은_발생하지_않는다() {
        // given
        User user = new User(10L, "javajigi", "password", "name", "javajigi@slipp.net");

        // when
        userRepository.save(user);

        // then
        assertThat(user.createdAt()).isNull();
        assertThat(user.updatedAt()).isNull();
    }

    @Test
    void 저장_이후_파라미터에_들어가는_객체에도_ID가_세팅된다() {
        // given
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");

        // when
        userRepository.save(user);

        // then
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void id로_조회하지_않아도_영속성_컨텍스트에_저장된다() {
        // given
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
        em.flush();
        em.clear();

        // when
        assertThat(em.contains(user)).isFalse();
        User found = userRepository.findByUserId("javajigi").get();

        // then
        assertThat(em.contains(found)).isTrue();
    }

    @Test
    void id로_조회하지_않아도_영속성_컨텍스트에_저장된다_그치만_똑같이_조회하면_쿼리는_계속_나간다() {
        // given
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
        em.flush();
        em.clear();

        // when
        User found1 = userRepository.findByUserId("javajigi").get();
        User found2 = userRepository.findByUserId("javajigi").get();
        User found3 = userRepository.findByUserId("javajigi").get();
        User found4 = userRepository.findByUserId("javajigi").get();

        // then
        assertThat(found1).isEqualTo(found2);
        assertThat(found1).isEqualTo(found3);
        assertThat(found1).isEqualTo(found4);
    }
}
