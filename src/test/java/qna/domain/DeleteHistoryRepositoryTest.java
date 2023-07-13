package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DeleteHistoryRepositoryTest extends RepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void deleteHistory를_저장한다() {
        final User user = UserFixture.fixture();
        
        em.persist(user);

        DeleteHistory deleteHistory = new DeleteHistory(
                ContentType.ANSWER,
                1L,
                user
        );
        deleteHistoryRepository.save(deleteHistory);

        DeleteHistory found = deleteHistoryRepository.findById(1L).get();

        assertThat(deleteHistory).isSameAs(found);
    }
}
