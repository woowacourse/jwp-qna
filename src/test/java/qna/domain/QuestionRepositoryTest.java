package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Nested
    class FindByIdAndDeleted {

        @Test
        void findByIdAndDeletedFalse() {
            // given
            Question question = new Question("나가", "응");
            Question savedQuestion = questionRepository.save(question);

            // when
            Optional<Question> maybeQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());

            // then
            Question findQuestion = maybeQuestion.get();
            assertAll(
                    () -> assertThat(maybeQuestion).isNotNull(),
                    () -> assertThat(findQuestion.getTitle()).isEqualTo("나가"),
                    () -> assertThat(findQuestion.getContents()).isEqualTo("응")
            );
        }

        @Test
        void findByIdAndDeletedTrue() {
            // given
            Question question = new Question("나가", "응");
            question.setDeleted(true);
            Question savedQuestion = questionRepository.save(question);

            // when
            Optional<Question> maybeQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());

            // then
            assertThat(maybeQuestion).isEmpty();
        }

        @Test
        void findByWrongIdAndDeletedFalse() {
            // given
            Long questionWrongId = -1L;

            // when
            Optional<Question> maybeQuestion = questionRepository.findByIdAndDeletedFalse(questionWrongId);

            // then
            assertThat(maybeQuestion).isEmpty();
        }

    }

}