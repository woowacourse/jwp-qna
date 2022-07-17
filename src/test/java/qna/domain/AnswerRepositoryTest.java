package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AnswerRepositoryTest {

	private final AnswerRepository answerRepository;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;

	AnswerRepositoryTest(AnswerRepository answerRepository, UserRepository userRepository,
		QuestionRepository questionRepository) {
		this.answerRepository = answerRepository;
		this.userRepository = userRepository;
		this.questionRepository = questionRepository;
	}

	@DisplayName("Answer를 저장한다.")
	@Test
	void save() {
		User writer = userRepository.save(UserTest.JAVAJIGI);
		Question question = questionRepository.save(QuestionTest.Q1.writeBy(writer));

		Answer answer = answerRepository.save(new Answer(writer, question, "answer content"));

		Answer findAnswer = answerRepository.findById(answer.getId()).get();
		assertAll(
			() -> assertThat(answer).isEqualTo(findAnswer),
			() -> assertThat(answer.isOwner(writer)).isTrue()
		);
	}
}
