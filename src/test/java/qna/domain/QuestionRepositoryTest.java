package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class QuestionRepositoryTest {

	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;

	QuestionRepositoryTest(QuestionRepository questionRepository, UserRepository userRepository) {
		this.questionRepository = questionRepository;
		this.userRepository = userRepository;
	}

	@DisplayName("Question을 저장한다.")
	@Test
	void save() {
		User writer = userRepository.save(UserTest.JAVAJIGI);
		Question question = questionRepository.save(QuestionTest.Q1.writeBy(writer));

		Question findQuestion = questionRepository.findById(question.getId()).get();

		assertAll(
			() -> assertThat(findQuestion).isEqualTo(question),
			() -> assertThat(findQuestion.isOwner(writer)).isTrue()
		);
	}
}
