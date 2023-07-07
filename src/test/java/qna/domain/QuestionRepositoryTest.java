package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 질문을_저장한다() {
        // when
        Question actual = questionRepository.save(Q1);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 질문을_조회한다() {
        // given
        Question actual = questionRepository.save(Q1);

        // when
        Question expected = questionRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
