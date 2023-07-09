package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.AnswerFixture;
import qna.fixture.QuestionFixture;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("특정 질문의 id로 삭제되지 않은 답변들을 조회한다.")
    void findByDeletedFalse() {
        // given
        final Answer savedA1 = answerRepository.save(AnswerFixture.A1());
        final Answer savedA2 = answerRepository.save(AnswerFixture.A2());
        savedA2.setDeleted(true);

        // when
        final List<Answer> unDeletedQuestions = answerRepository.findByQuestionIdAndDeletedFalse(QuestionFixture.Q1().getId());

        //then
        assertThat(unDeletedQuestions).containsExactly(savedA1);
    }


    @Nested
    @DisplayName("id 삭제되지 않은 답변을 조회한다.")
    public class FindByIdAndDeletedFalse {

        @Test
        @DisplayName("해당 id로 삭제되지 않은 답변이 존재한다.")
        void findUnDeletedQuestionById() {
            // given
            final Answer savedA1 = answerRepository.save(AnswerFixture.A1());

            // when
            final Optional<Answer> foundQuestion = answerRepository.findByIdAndDeletedFalse(savedA1.getId());

            //then
            assertThat(foundQuestion.get()).isEqualTo(savedA1);
        }

        @Test
        @DisplayName("해당 id로 삭제되지 않은 답변이 존재하지 않는다.")
        void findDeletedQuestionById() {
            // given
            final Answer savedA1 = answerRepository.save(AnswerFixture.A1());
            savedA1.setDeleted(true);

            // when
            final Optional<Answer> foundQuestion = answerRepository.findByIdAndDeletedFalse(savedA1.getId());

            //then
            assertThat(foundQuestion).isEmpty();
        }
    }

}
