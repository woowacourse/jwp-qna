package qna.domain;

import static org.assertj.core.api.Assertions.*;

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
		User user = new User("does", "1234", "더즈", "ldk980130@gmail.com");
		userRepository.save(user);
		User does = userRepository.findByUserId("does")
			.get();

		assertThat(does).isEqualTo(user);
	}
}
