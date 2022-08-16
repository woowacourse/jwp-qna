package qna.repository.datajpa;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.User;
import qna.repository.UserRepository;

@Repository
@Transactional(readOnly = true)
public class HibernateUserRepository implements UserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public HibernateUserRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User save(final User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(final String userId) {
        return Optional.empty();
    }
}
