package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findByDeletedFalse() {
        // given
        final Question question = new Question("test", "testtest");
        final Question savedQuestion = questionRepository.save(question);

        // when
        final List<Question> actual = questionRepository.findByDeletedFalse();

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(savedQuestion);
    }

    @Test
    void findByIdAndDeletedFalse() {
        // given
        final Question question = new Question("test", "testtest");
        final Question savedQuestion = questionRepository.save(question);

        // when
        final Question actual = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId()).get();

        // then
        assertThat(actual).isEqualTo(savedQuestion);
    }
}
