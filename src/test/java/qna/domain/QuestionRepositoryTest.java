package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.fixture.QuestionFixture;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("삭제되지 않은 질문들을 조회한다.")
    void findByDeletedFalse() {
        // given
        final Question savedQ1 = questionRepository.save(QuestionFixture.Q1());
        final Question savedQ2 = questionRepository.save(QuestionFixture.Q2());
        savedQ2.setDeleted(true);

        // when
        final List<Question> unDeletedQuestions = questionRepository.findByDeletedFalse();

        //then
        assertThat(unDeletedQuestions).containsExactly(savedQ1);
    }


    @Nested
    @DisplayName("id 삭제되지 않은 질문을 조회한다.")
    public class FindByIdAndDeletedFalse {

        @Test
        @DisplayName("해당 id로 삭제되지 않은 질문이 존재한다.")
        void findUnDeletedQuestionById() {
            // given
            final Question savedQ1 = questionRepository.save(QuestionFixture.Q1());

            // when
            final Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(savedQ1.getId());

            //then
            assertThat(foundQuestion.get()).isEqualTo(savedQ1);
        }

        @Test
        @DisplayName("해당 id로 삭제되지 않은 질문이 존재하지 않는다.")
        void findDeletedQuestionById() {
            // given
            final Question savedQ1 = questionRepository.save(QuestionFixture.Q1());
            savedQ1.setDeleted(true);

            // when
            final Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(savedQ1.getId());

            //then
            assertThat(foundQuestion).isEmpty();
        }
    }

}
