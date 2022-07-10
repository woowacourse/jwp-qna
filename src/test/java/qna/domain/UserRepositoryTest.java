package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void 저장하려는_객체_ID가_유효하고_영속성_컨텍스트에_없는_값이면_해당_ID로_DB를_조회하고_없다면_새로운_ID를_할당한_새_객체_반환() {
        // given
        final User klay = new User(1L, "klay", "password", "name", "klay@slipp.net");
        final User actual = userRepository.save(klay);

        // when, then
        assertAll(
                () -> assertThat(actual).isNotEqualTo(klay)
        );
    }

    @Test
    void 저장하려는_객체_ID가_유효하고_영속성_컨텍스트에_있는_값이면_영속된_객체_데이터를_바꾸고_플러시할_때_해당_ID의_데이터를_업데이트하고_영속_상태인_객체_반환() {
        // given
        final User user = new User(null, "firstUser", "1234", "hello", "klay@naver.com");
        userRepository.save(user);

        // when
        final User klay = new User(user.getId(), "klay", "password", "name", "klay@slipp.net");
        final User actual = userRepository.save(klay);
        userRepository.flush(); // update

        // then
        assertAll(
                () -> assertThat(actual).isNotEqualTo(klay),
                () -> assertThat(actual).isEqualTo(user)
        );
    }

    @Test
    void 유저_Id로_유저_조회() {
        // given
        final User user = new User("pobi", "1234", "javajigi", "javajigi@naver.com");
        userRepository.save(user);

        // when
        final User foundUser = userRepository.findByUserId("pobi")
                .orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(user).isEqualTo(foundUser);
    }
}
