package qna.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerRepositoryTest extends RepositoryTest {

    @Test
    void Answer를_저장하고_조회한다() {
        Answer answer = AnswerTest.A1;

        answerRepository.save(answer);
        Answer saved = answerRepository.findById(answer.getId()).get();

        assertThat(answer).isSameAs(saved);
    }
}
