package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;

    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    void setUp() {
        // given
        answer1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "answer1");
        answer2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "answer2");
        answerRepository.saveAll(List.of(answer1, answer2));

        answer1.setDeleted(true);
    }

    @Test
    void 질문_id가_일치하고_삭제되지_않은_답변_조회() {
        // when
        final List<Answer> foundQuestions = answerRepository.findByQuestionIdAndDeletedFalse(QuestionTest.Q1.getId());

        // then
        assertThat(foundQuestions).containsExactly(answer2);
    }

    @Test
    void id가_일치하고_삭제되지_않은_답변_조회() {
        // when
        final Optional<Answer> foundForDeleted = answerRepository.findByIdAndDeletedFalse(answer1.getId());
        final Optional<Answer> foundForNotDeleted = answerRepository.findByIdAndDeletedFalse(answer2.getId());

        // then
        assertAll(
                () -> assertThat(foundForDeleted).isEqualTo(Optional.empty()),
                () -> assertThat(foundForNotDeleted).isEqualTo(Optional.of(answer2))
        );
    }
}
