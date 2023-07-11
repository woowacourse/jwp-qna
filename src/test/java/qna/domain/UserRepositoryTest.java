package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserFixture.JAVAJIGI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    private final UserRepository userRepository;

    public UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DisplayName("사용자를 저장한다.")
    @Test
    void save() {
        // given
        // when
        final User saved = userRepository.save(JAVAJIGI);

        // then
        assertThat(userRepository.findById(saved.getId()).get())
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("사용자를 로그인 아이디로 조회한다.")
    @Test
    void findByUserId() {
        // given
        final User saved = userRepository.save(JAVAJIGI);

        // when
        final User actual = userRepository.findByUserId(saved.getUserId()).get();

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("사용자의 이메일 변경을 감지해 갱신한다.")
    @Test
    void update() {
        // given
        final User saved = userRepository.save(JAVAJIGI);

        // when
        final String updateEmail = "doyoungplay@gmail.com";
        saved.setEmail(updateEmail);

        // then
        final User updated = userRepository.findById(saved.getId()).get();
        assertThat(updated.getEmail())
                .isEqualTo(updateEmail);
    }

    @DisplayName("사용자를 삭제한다.")
    @Test
    void delete() {
        // given
        final User saved = userRepository.save(JAVAJIGI);

        // when
        userRepository.delete(saved);

        // then
        assertThat(userRepository.findById(saved.getId()))
                .isEmpty();
    }

    @DisplayName("식별자로 조회한 사용자는 서로 동일하다.")
    @Test
    void identity() {
        // given
        final User saved = userRepository.save(JAVAJIGI);
        final User actual = userRepository.findById(saved.getId()).get();
        final User actual2 = userRepository.findByUserId(saved.getUserId()).get();

        // when
        // then
        assertThat(saved).isSameAs(actual);
        assertThat(actual).isSameAs(actual2);
    }
}
