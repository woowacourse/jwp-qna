package qna.fixture;

import qna.domain.User;
import qna.repository.UserRepository;

public class UserRepositoryFixture {

    private final UserRepository userRepository;

    public UserRepositoryFixture(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(final String userId, final String password, final String name, final String email) {
        return userRepository.save(new User(userId,password, name, email));
    }
}
