package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class DeleteHistoryRepositoryTest {


    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 삭제이력을_저장한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));

        // when
        DeleteHistory actual = deleteHistoryRepository.save(
                new DeleteHistory(ContentType.QUESTION, 1L, user, LocalDateTime.now()));

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 삭제이력을_조회한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        DeleteHistory actual = deleteHistoryRepository.save(
                new DeleteHistory(ContentType.QUESTION, 1L, user, LocalDateTime.now()));

        // when
        DeleteHistory expected = deleteHistoryRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
