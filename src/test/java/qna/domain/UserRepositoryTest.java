package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.TestConstructor.AutowireMode.*;

@DataJpaTest
@TestConstructor(autowireMode = ALL)
class UserRepositoryTest {
    
    private UserRepository userRepository;

    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findById() {
        // given
        User saved = userRepository.save(UserFixture.javajigi());
        // when
        Optional<User> found = userRepository.findById(saved.getId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get().getUserId()).isEqualTo(UserFixture.javajigi().getUserId())
        );
    }

    @Test
    void findAll() {
        // given
        userRepository.save(UserFixture.javajigi());
        userRepository.save(UserFixture.sanjigi());
        // when
        List<User> users = userRepository.findAll();
        // then
        assertThat(users).hasSize(2);
    }

    @Test
    void countTest() {
        // given
        userRepository.save(UserFixture.javajigi());
        userRepository.save(UserFixture.sanjigi());
        // when
        long count = userRepository.count();
        // then
        assertThat(count).isEqualTo(2);

    }

    @Test
    void deleteTest() {
        // given
        User saved = userRepository.save(UserFixture.javajigi());
        // when
        userRepository.delete(saved);
        Optional<User> found = userRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void deleteByIdTest() {
        // given
        User saved = userRepository.save(UserFixture.javajigi());
        // when
        userRepository.deleteById(saved.getId());
        Optional<User> found = userRepository.findById(saved.getId());
        // then
        assertThat(found).isEmpty();
    }

    @Test
    void existsTest() {
        // given
        User saved = userRepository.save(UserFixture.javajigi());
        // when
        boolean isExists = userRepository.existsById(saved.getId());
        // then
        assertThat(isExists).isTrue();
    }

    @Test
    void findByUserId() {
        // given
        User saved = userRepository.save(UserFixture.javajigi());
        // when
        Optional<User> found = userRepository.findByUserId(saved.getUserId());
        // then
        assertAll(
                () -> assertThat(found).isPresent(),
                () -> assertThat(found.get().getUserId()).isEqualTo(UserFixture.javajigi().getUserId())
        );
    }
}
