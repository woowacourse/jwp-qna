package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 삭제되지_않은_질문을_조회() {
        // given
        final Question question1 = new Question("question1", "question content1");
        final Question question2 = new Question("question2", "question content2");
        final Question question3 = new Question("question3", "question content3");
        questionRepository.saveAll(List.of(question1, question2, question3));

        question1.setDeleted(true);

        // when
        final List<Question> notDeletedQuestions = questionRepository.findByDeletedFalse();

        // then
        assertThat(notDeletedQuestions).containsExactly(question2, question3);
    }

    @Test
    void id가_일치하고_삭제되지_않은_질문_조회() {
        // given
        final Question question1 = new Question("question1", "question content1");
        final Question question2 = new Question("question2", "question content2");
        questionRepository.saveAll(List.of(question1, question2));

        question1.setDeleted(true);

        // when
        final Optional<Question> foundQuestion1 = questionRepository.findByIdAndDeletedFalse(question1.getId());
        final Optional<Question> foundQuestion2 = questionRepository.findByIdAndDeletedFalse(question2.getId());

        // then
        assertAll(
                () -> assertThat(foundQuestion1).isEqualTo(Optional.empty()),
                () -> assertThat(foundQuestion2).isEqualTo(Optional.of(question2))
        );
    }
}
