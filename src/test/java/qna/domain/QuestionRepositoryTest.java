package qna.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends RepositoryTest{

    @Test
    void Question을_저장한다() {
        Question save = questionRepository.save(QuestionTest.Q1);
        Question found = questionRepository.findById(save.getId()).get();

        assertThat(save).isSameAs(found);
    }
}
