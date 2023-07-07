package qna.domain;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

  @Autowired
  private QuestionRepository questionRepository;

  @Test
  @DisplayName("삭제되지 않은 Question들을 조회한다.")
  void findByDeletedFalse() {
    final List<Question> questions = questionRepository.findByDeletedFalse();

    Assertions.assertThat(questions).hasSize(1);
  }

  @Test
  @DisplayName("id를 통해 삭제되지 않은 Question을 조회한다.")
  void findByIdAndDeletedFalse() {
    final Long questionId = 1L;

    final Optional<Question> question = questionRepository.findByIdAndDeletedFalse(
        questionId);

    Assertions.assertThat(question).isPresent();
  }
}
