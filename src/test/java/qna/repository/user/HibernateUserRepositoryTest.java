package qna.repository.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.domain.User;
import qna.repository.datajpa.HibernateUserRepository;

@Import(HibernateUserRepository.class)
@DataJpaTest
public class HibernateUserRepositoryTest {

    @Autowired
    private HibernateUserRepository userRepository;

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
}
