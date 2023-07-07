package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.AnswerTest.A1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void 답변을_저장한다() {
        // when
        Answer actual = answerRepository.save(A1);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 답변을_조회한다() {
        // given
        Answer actual = answerRepository.save(A1);

        // when
        Answer expected = answerRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
