package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @BeforeEach
    void setUp() {
        questions.deleteAll();
    }

    @DisplayName("질문을 저장한다.")
    @Test
    void save() {
        Question question = new Question("title", "contents");

        Question actual = questions.save(question);

        assertThat(actual == question).isTrue();
    }

    @DisplayName("deleted가 false인 질문을 모두 찾는다.")
    @Test
    void findByDeletedFalse() {
        Question question1 = questions.save(new Question("title1", "contents1"));
        Question question2 = questions.save(new Question("title2", "contents2"));

        List<Question> actual = questions.findByDeletedFalse();

        assertThat(actual).hasSize(2);
    }

    @DisplayName("id로 deleted가 false인 질문을 찾는다.")
    @Test
    void findByIdAndDeletedFalse() {
        Question question1 = questions.save(new Question("title1", "contents1"));
        Question question2 = questions.save(new Question("title2", "contents2"));

        Optional<Question> actual = questions.findByIdAndDeletedFalse(question1.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get().getTitle()).isEqualTo("title1")
        );
    }
}
