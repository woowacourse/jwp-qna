package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    private Question question1;

    @BeforeEach
    void setUp() {
        question1 = questionRepository.save(new Question("제목1", "내용1"));

    }

    @DisplayName("deleted가 false인 Question들을 조회한다.")
    @Test
    void findByDeletedFalse() {
        Question question2 = questionRepository.save(new Question("제목2", "내용2"));
        question2.setDeleted(true);

        List<Question> questions = questionRepository.findByDeletedFalse();

        assertAll(
                () -> assertThat(questions).hasSize(1),
                () -> assertThat(questions).containsExactly(question1)
        );
    }

    @DisplayName("deleted가 false인 단건 Question을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {

        Question question = questionRepository.findByIdAndDeletedFalse(question1.getId()).get();

        assertThat(question).isSameAs(question1);
    }

    @DisplayName("deleted가 false인 단건 Question을 조회할 때, 없으면 empty()를 반환한다.")
    @Test
    void findByIdAndDeletedFalseWhenFailure() {

        question1.setDeleted(true);

        assertThat(questionRepository.findByIdAndDeletedFalse(question1.getId())).isEmpty();
    }
}
