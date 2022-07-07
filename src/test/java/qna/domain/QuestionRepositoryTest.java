package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void saveQuestion() {
        // given
        Question question = new Question("제목","내용");

        // when
        Question actual = questionRepository.save(question);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(question).isSameAs(actual);
    }
}
