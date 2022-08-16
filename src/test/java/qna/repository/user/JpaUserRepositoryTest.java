package qna.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.User;
import qna.repository.datajpa.JpaUserRepository;

@DataJpaTest
public class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("사용자를 저장한다.")
    @Test
    void save() {
        // given
        final User user = User.builder()
                .userId("hdg3052")
                .password("1234adfg!")
                .email("kun@email.com")
                .name("kun")
                .build();

        // when
        final User savedUser = userRepository.save(user);

        // then
        assertThat(savedUser.getId())
                .isNotNull();
    }

    @DisplayName("이미 저장된 사용자를 다시 저장한다.")
    @Test
    void save_merge() {
        // given
        final User user = User.builder()
                .userId("hdg3052")
                .password("1234adfg!")
                .email("kun@email.com")
                .name("kun")
                .build();
        final User savedUser = userRepository.save(user);

        user.setName("hong");

        // when
        final User newuser = userRepository.save(user);

        // then
        assertThat(savedUser.getId())
                .isNotNull();
    }
}
