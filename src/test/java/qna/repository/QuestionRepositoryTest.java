package qna.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

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
}
