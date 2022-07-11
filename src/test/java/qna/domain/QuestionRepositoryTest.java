package qna.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class QuestionRepositoryTest {

	private final QuestionRepository questionRepository;

	QuestionRepositoryTest(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	@DisplayName("Question을 저장한다.")
	@Test
	void save() {
		Question question = new Question("제이슨", "커피는 무슨 맛인가요");
		questionRepository.save(question);

		Question findQuestion = questionRepository.findById(question.getId()).get();

		assertThat(findQuestion).isEqualTo(question);
	}
}
