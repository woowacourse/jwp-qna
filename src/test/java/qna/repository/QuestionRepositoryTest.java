package qna.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.Question;
import qna.repository.config.RepositoryTestConfig;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("삭제되지 않은 질문을 조회한다.")
    void findByDeletedFalse() {
        // given
        final Question expected = new Question("title", "contents");
        questionRepository.save(expected);

        // when
        List<Question> actual = questionRepository.findByDeletedFalse();

        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    @DisplayName("삭제되지 않은 질문중에 식별자로 조회한다.")
    void findByIdAndDeletedFalse() {
        // given
        final Question expected = new Question("title", "contents");
        questionRepository.save(expected);

        // when
        Long expectedId = expected.getId();
        Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(expectedId);

        // then
        assertThat(actual).contains(expected);
    }
}
