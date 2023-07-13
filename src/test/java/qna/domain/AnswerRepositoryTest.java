package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerRepositoryTest extends RepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void Answer를_저장하고_조회한다() {
        final User user = UserFixture.fixture();
        em.persist(user);
        final Question question = new Question("질문입니다", "질문이에요", user);
        em.persist(question);
        final Answer answer = answerRepository.save(new Answer(user, question, "자문자답입니다"));
        Answer saved = answerRepository.findById(answer.getId()).get();

        assertThat(answer).isSameAs(saved);
    }
}
