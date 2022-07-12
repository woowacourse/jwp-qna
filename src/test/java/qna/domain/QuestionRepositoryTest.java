package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("deleted가 false인 질문 조회")
    @Test
    void findByDeletedFalse() {
        //given
        final List<Question> questions = questionRepository.saveAll(Arrays.asList(QuestionTest.Q1, QuestionTest.Q2));

        //when
        final List<Question> findQuestions = questionRepository.findByDeletedFalse();

        //then
        assertThat(questions).containsExactlyInAnyOrderElementsOf(findQuestions);
    }

    @DisplayName("deleted가 false인 질문을 id로 조회 - 존재 O")
    @Test
    void findByIdAndDeletedFalse() {
        //given
        final Question question = questionRepository.save(QuestionTest.Q1);

        //when
        final Optional<Question> questionById = questionRepository.findByIdAndDeletedFalse(question.getId());

        //then
        assertThat(questionById.get()).isEqualTo(question);
    }

    @DisplayName("deleted가 false인 질문을 id로 조회 - 존재 X")
    @Test
    void findByIdAndDeletedFalseIsNotExist() {
        //given
        final Question question = questionRepository.save(QuestionTest.Q1);
        question.setDeleted(true);

        //when & then
        assertThat(questionRepository.findByIdAndDeletedFalse(question.getId())).isEmpty();
    }
}
