package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

@RepositoryTest
class DeleteHistoryRepositoryTest {

    private DeleteHistoryRepository deleteHistoryRepository;
    private UserRepository userRepository;

    public DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistoryRepository, UserRepository userRepository) {
        this.deleteHistoryRepository = deleteHistoryRepository;
        this.userRepository = userRepository;
    }

    @Test
    void save() {
        // given
        final User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, user, LocalDateTime.now());

        // when
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        // then
        assertAll(
                () -> assertThat(savedDeleteHistory).isNotNull(),
                () -> assertThat(savedDeleteHistory.getContentType()).isEqualTo(ContentType.QUESTION),
                () -> assertThat(savedDeleteHistory.getContentId()).isEqualTo(1L),
                () -> assertThat(savedDeleteHistory.getDeletedBy()).isEqualTo(user)
        );
    }

    @Test
    void findAll() {
        // given
        final User user1 = new User("javajigi", "password", "name", "javajigi@slipp.net");
        final User user2 = new User("sanjigi", "password", "name", "sanjigi@slipp.net");
        userRepository.save(user1);
        userRepository.save(user2);
        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, 1L, user1, LocalDateTime.now());
        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.ANSWER, 2L, user2, LocalDateTime.now());

        deleteHistoryRepository.save(deleteHistory1);
        deleteHistoryRepository.save(deleteHistory2);

        // when
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();

        // then
        assertAll(
                () -> assertThat(deleteHistories).hasSize(2),
                () -> assertThat(deleteHistories.get(0)).usingRecursiveComparison().isEqualTo(deleteHistory1),
                () -> assertThat(deleteHistories.get(1)).usingRecursiveComparison().isEqualTo(deleteHistory2)
        );
    }

    @Test
    void delete() {
        // given
        final User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(user);
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, user, LocalDateTime.now());
        deleteHistoryRepository.save(deleteHistory);

        // when
        deleteHistoryRepository.delete(deleteHistory);

        // then
        List<DeleteHistory> deleteHistories = deleteHistoryRepository.findAll();
        assertThat(deleteHistories).isEmpty();
    }

}
