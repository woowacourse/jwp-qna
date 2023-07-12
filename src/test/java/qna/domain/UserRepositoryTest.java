package qna.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends RepositoryTest {

    @Test
    void 저장시에_createdAt이_생성된다() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
        User found = userRepository.findById(user.getId()).get();

        assertThat(found.getCreatedAt()).isNotNull();
    }
}
