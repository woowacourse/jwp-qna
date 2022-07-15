package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("userId로 사용자 조회 - 존재 O")
    void findByUserId() {
        //given
        final User user = userRepository.save(UserTest.JAVAJIGI);

        //when & then
        assertThat(userRepository.findByUserId(user.getUserId())).contains(user);
    }

    @Test
    @DisplayName("userId로 사용자 조회 - 존재 X")
    void findByUserIdIsNotExist() {
        //given
        userRepository.save(UserTest.JAVAJIGI);

        //when & then
        assertThat(userRepository.findByUserId("dummy")).isEmpty();
    }
}
