package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UserRepositoryTest extends RepositoryTest {

    private final UserRepository userRepository;

    public UserRepositoryTest(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findByUserId() {
        User user = new User("userId", "password", "name", "email");
        userRepository.save(user);
        User findUser = userRepository.findByUserId(user.getUserId()).get();

        assertThat(findUser).isNotNull();
    }
}
