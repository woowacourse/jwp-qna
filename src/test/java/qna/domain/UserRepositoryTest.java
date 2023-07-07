package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("유저를 저장한다.")
	void save() {
		// given
		// when
		final User actualUser = userRepository.save(UserTest.JAVAJIGI);

		// then
		assertThat(actualUser).usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(UserTest.JAVAJIGI);
	}

	@Test
	void findByUserId() {
		final User user = new User("vero", "1234", "베로", "vero@email.com");
		final User savedUser = userRepository.save(user);
		final User actualUser = userRepository.findByUserId(user.getUserId()).get();

		assertThat(savedUser == actualUser).isTrue();
	}
}
