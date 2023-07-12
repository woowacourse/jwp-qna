package qna.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.JpaConfig;

@DataJpaTest
@Import(JpaConfig.class)
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void Question_을_저장하고_조회한다() {
        Question question = QuestionTest.Q1;

        questionRepository.save(question);
        Question saved = questionRepository.findById(question.getId()).get();

        Assertions.assertThat(question).isSameAs(saved);
    }
}
