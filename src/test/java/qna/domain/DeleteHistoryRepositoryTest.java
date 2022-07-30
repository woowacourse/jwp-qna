package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import qna.JpaAuditingConfiguration;

@DataJpaTest
@Import(JpaAuditingConfiguration.class)
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
        DeleteHistory savedDeleteHistory = createDeleteHistory(JAVAJIGI);
        synchronize();

        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(savedDeleteHistory);
    }

    @Test
    void findAll() {
        DeleteHistory savedDeleteHistory1 = createDeleteHistory(JAVAJIGI);
        DeleteHistory savedDeleteHistory2 = createDeleteHistory(SANJIGI);
        synchronize();

        List<DeleteHistory> result = deleteHistoryRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteById() {
        DeleteHistory savedDeleteHistory = createDeleteHistory(JAVAJIGI);
        synchronize();

        deleteHistoryRepository.deleteById(savedDeleteHistory.getId());
        synchronize();

        Optional<DeleteHistory> result = deleteHistoryRepository.findById(savedDeleteHistory.getId());

        assertThat(result).isEmpty();
    }

    DeleteHistory createDeleteHistory(User user) {
        User savedUser = userRepository.save(user);

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, 1L, savedUser);
        return deleteHistoryRepository.save(deleteHistory);
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
