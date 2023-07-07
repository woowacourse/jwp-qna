package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("id를 통해 사용자를 조회한다.")
  void findByUserIdTest() throws Exception {
    //given
    final String userId = "user";

    //when
    final Optional<User> user = userRepository.findByUserId(userId);

    //then
    assertThat(user).isPresent();
  }
}
