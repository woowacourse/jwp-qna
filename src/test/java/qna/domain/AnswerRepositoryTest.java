package qna.domain;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

  @Autowired
  private AnswerRepository answerRepository;

  @Test
  @DisplayName("Question Id를 입력하면 삭제되지 않은 Answer들을 조회할 수 있다.")
  void findByQuestionIdAndDeletedFalse_success() {
    //given, when
    final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(1L);

    //then
    Assertions.assertThat(actual).hasSize(1);
  }

  @Test
  @DisplayName("Answer Id를 입력하면 삭제되지 않은 Answer를 조회할 수 있다.")
  void findByIdAndDeletedFalse_present() {
    //given, when
    final Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(1L);

    //then
    Assertions.assertThat(actual).isPresent();
  }
}
