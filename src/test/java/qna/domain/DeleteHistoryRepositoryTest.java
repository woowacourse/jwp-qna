package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save() {
        User user = new User("Rex", "rex1!", "렉스", "rex#woowa.com");
        User savedUser = userRepository.save(user);
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, savedUser);

        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        assertThat(savedDeleteHistory).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(deleteHistory);
    }

    @Test
    void findById() {
        User user = new User("Rex", "rex1!", "렉스", "rex#woowa.com");
        User savedUser = userRepository.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, savedUser);
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);
        entityManager.clear();

        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(savedDeleteHistory);
    }

    @Test
    void findAll() {
        User user1 = new User("Rex", "rex1!", "렉스", "rex@woowa.com");
        User savedUser1 = userRepository.save(user1);
        User user2 = new User("Momo", "momo1!", "모모", "momo@woowa.com");
        User savedUser2 = userRepository.save(user2);

        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.ANSWER, 1L, savedUser1);
        deleteHistoryRepository.save(deleteHistory1);
        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.QUESTION, 2L, savedUser2);
        deleteHistoryRepository.save(deleteHistory2);
        entityManager.clear();

        List<DeleteHistory> result = deleteHistoryRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        User user = new User("Rex", "rex1!", "렉스", "rex#woowa.com");
        User savedUser = userRepository.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, savedUser);
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);
        entityManager.clear();

        deleteHistoryRepository.deleteById(savedDeleteHistory.getId());
        deleteHistoryRepository.flush();

        entityManager.clear();
        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isEmpty();
    }
}
