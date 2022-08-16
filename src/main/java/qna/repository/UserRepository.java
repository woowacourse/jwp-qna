package qna.repository;

import java.util.Optional;
import qna.domain.User;

public interface UserRepository {

    User save(final User user);

    Optional<User> findByUserId(String userId);
}
