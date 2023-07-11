package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static qna.fixtures.UserFixture.JAVAJIGI;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixtures.QuestionFixture;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void 질문ID를_입력_받아_삭제되지_않은_답변을_조회한다() {
        // given
        answerRepository.save(new Answer(JAVAJIGI, QuestionFixture.Q1, "Answers Contents1"));
        Answer answer = answerRepository.save(new Answer(JAVAJIGI, QuestionFixture.Q1, "Answers Contents2"));
        answer.changeDeleted(true);

        Answer diffrentQuestionIdAnswer = new Answer(JAVAJIGI, QuestionFixture.Q2, "Answers Contents3");
        answerRepository.save(diffrentQuestionIdAnswer);

        // when
        List<Answer> results = answerRepository.findByQuestionIdAndDeletedFalse(QuestionFixture.Q1.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(results).hasSize(1);
            softly.assertThat(results).doesNotContain(diffrentQuestionIdAnswer);
        });
    }

    @Test
    void ID를_입력_받아_삭제되지_않은_답변을_조회한다() {
        // given
        answerRepository.save(new Answer(1L, JAVAJIGI, QuestionFixture.Q1, "Answers Contents1"));
        answerRepository.save(new Answer(2L, JAVAJIGI, QuestionFixture.Q1, "Answers Contents2"));

        // when
        Optional<Answer> byIdAndDeletedFalse = answerRepository.findByIdAndDeletedFalse(1L);

        // then
        assertThat(byIdAndDeletedFalse).isPresent();
    }
}
