package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixtures.AnswerFixture.A1;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void auditingTest() {
        // given
        final Answer saved = answerRepository.save(A1);
        answerRepository.flush();

        // when
        final Answer answer = answerRepository.findById(saved.getId()).orElseThrow();

        // then
        final LocalDateTime createdAt = answer.getCreatedAt();
        assertThat(createdAt).isNotNull();
    }
}
