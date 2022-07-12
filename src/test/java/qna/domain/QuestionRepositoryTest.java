package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("삭제되지 않은 질문을 조회한다.")
    @Test
    void findByDeletedFalse() {
        questionRepository.save(Q1);
        questionRepository.save(Q2);

        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 질문을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        questionRepository.save(Q1);

        Optional<Question> question = questionRepository.findByIdAndDeletedFalse(Q1.getId());

        assertThat(question).isPresent();
        Question actual = question.get();
        assertThat(Q1).isEqualTo(actual);
    }
}
