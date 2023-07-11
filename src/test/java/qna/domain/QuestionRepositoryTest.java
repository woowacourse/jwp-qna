package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        // given
        // when
        final Question savedQuestion = questionRepository.save(QuestionTest.Q1);

        // then
        assertThat(savedQuestion).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(QuestionTest.Q1);
    }

    @Test
    @DisplayName("삭제되지 않은 질문들을 조회한다.")
    void findByDeletedFalse() {
        // given
        final Question savedQuestion1 = questionRepository.save(QuestionTest.Q1);
        final Question savedQuestion2 = questionRepository.save(QuestionTest.Q2);

        // when
        final List<Question> questions = questionRepository.findByDeletedFalse();

        // then
        assertThat(questions).usingRecursiveComparison()
            .isEqualTo(Arrays.asList(savedQuestion1, savedQuestion2));
    }

    @Test
    @DisplayName("삭제되지 않은 질문을 id로 조회한다.")
    void findByIdAndDeletedFalse() {
        // given
        final Question question = new Question("제목1", "내용1");
        final Question question2 = new Question("제목2", "내용2");
        question2.setDeleted(true);

        // when
        final Question savedQuestion = questionRepository.save(question);
        final Question deletedQuestion = questionRepository.save(question2);

        // then
        assertAll(
            () -> assertThat(
                questionRepository.findByIdAndDeletedFalse(savedQuestion.getId())).isPresent(),
            () -> assertThat(
                questionRepository.findByIdAndDeletedFalse(deletedQuestion.getId())).isEmpty()
        );
    }
}
