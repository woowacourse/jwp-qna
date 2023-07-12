package qna.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteHistoryRepositoryTest extends RepositoryTest {

    @Test
    void deleteHistory를_저장한다() {
        final User savedUser = userRepository.save(UserTest.JAVAJIGI);
        DeleteHistory deleteHistory = new DeleteHistory(
                ContentType.ANSWER,
                1L,
                savedUser
        );
        deleteHistoryRepository.save(deleteHistory);

        DeleteHistory found = deleteHistoryRepository.findById(1L).get();

        assertThat(deleteHistory).isSameAs(found);
    }
}
