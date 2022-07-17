package qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserRepositoryTest {

	private final UserRepository userRepository;

	public UserRepositoryTest(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@DisplayName("User를 저장한다")
	@Test
	void save() {
		userRepository.save(UserTest.JAVAJIGI);

		Optional<User> user = userRepository.findByUserId(UserTest.JAVAJIGI.getUserId());

		assertThat(user)
			.map(User::getUserId)
			.get()
			.isEqualTo("javajigi");
	}

	@DisplayName("User 정보를 수정한다.")
	@Test
	void update() {
		User loginUser = userRepository.save(UserTest.JAVAJIGI);
		User target = UserTest.SANJIGI;

		User user = userRepository.findByUserId("javajigi").get();
		user.update(loginUser, target);

		assertThat(user.equalsNameAndEmail(target)).isTrue();
	}
}
