package qna.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import qna.domain.User;
import qna.repository.UserRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    User save(final User user);
}
