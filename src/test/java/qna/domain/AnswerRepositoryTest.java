package qna.domain;

import static org.assertj.core.api.Assertions.*;

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
		User user = userRepository.save(
			new User("does", "1234", "더즈", "ldk980130@gmail.com"));
		Question question = questionRepository.save(new Question("제이슨", "커피는 무슨 맛인가요"));

		Answer answer = new Answer(user, question, "아메리카노입니다.");
		answerRepository.save(answer);

		Answer findAnswer = answerRepository.findById(answer.getId()).get();

		assertThat(answer).isEqualTo(findAnswer);
	}
}
