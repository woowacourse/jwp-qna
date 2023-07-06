package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixtures.QuestionFixture.Q1;
import static qna.fixtures.QuestionFixture.Q2;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 삭제되지_않은_질문을_조회한다() {
        // given
        Question question = questionRepository.save(Q1);
        question.changeDeleted(true);
        questionRepository.save(Q2);

        // when
        List<Question> results = questionRepository.findByDeletedFalse();

        // then
        assertThat(results).hasSize(1);
    }

    @Test
    void ID를_입력_받아_삭제되지_않은_질문을_조회한다() {
        // given
        Question question = questionRepository.save(Q1);

        // when
        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(question.getId());

        // then
        assertThat(result).isPresent();
    }
}
