package qna.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import qna.fixture.UserFixture;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.UserFixture.SEONGHA;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 정보를 영속화 한다.")
    void persistUser() {
        // given
        final User user = SEONGHA();

        // when
        final User savedUser = userRepository.save(user);

        //then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(savedUser.getId()).isNotNull();
            softly.assertThat(savedUser).usingRecursiveComparison()
                    .ignoringFields("id", "createdAt", "updatedAt")
                    .isEqualTo(SEONGHA());
        });
    }

    @Test
    @DisplayName("영속화된 유저의 정보를 id를 통해 조회한다.")
    void findById() {
        // given
        final User user = SEONGHA();
        final User savedUser = userRepository.save(user);

        // when
        final User foundUser = userRepository.findById(savedUser.getId()).get();

        //then
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("영속화된 유저의 정보를 사용자아이디를 통해 조회한다.")
    void findByUserId() {
        // given
        final User user = SEONGHA();
        final User savedUser = userRepository.save(user);

        // when
        final User foundUser = userRepository.findByUserId(savedUser.getUserId()).get();

        //then
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("유저의 정보를 변경한다.")
    void updateUser() {
        // given
        final User user = SEONGHA();
        final User savedUser = userRepository.save(user);

        // when
        savedUser.update(savedUser, UserFixture.JAVAJIGI());
        final User actual = userRepository.findById(savedUser.getId()).get();

        //then
        assertThat(actual.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("유저의 정보를 삭제한다.")
    @Rollback(value = false)
    void deleteUser() {
        // given
        final User user = SEONGHA();
        final User savedUser = userRepository.save(user);

        // when
        userRepository.delete(savedUser);
        final Optional<User> actual = userRepository.findById(savedUser.getId());

        //then
        assertThat(actual).isEmpty();
    }
}
