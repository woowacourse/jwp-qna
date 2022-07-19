package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QuestionRepositoryTest extends RepositoryTest {

    @Autowired
    private QuestionRepository questions;

    private static Question question;

    @BeforeEach
    void setUp() {
        question = new Question("제목", "내용");
    }

    @DisplayName("질문 생성")
    @Test
    void save() {
        Question expected = question;

        Question actual = questions.save(expected);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("질문 조회")
    @Test
    void findById() {
        Question expected = questions.save(question);
        synchronize();

        Optional<Question> actual = questions.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("삭제되지 않은 질문 목록 조회")
    @Test
    void findByDeletedFalse() {
        Question question1 = questions.save(new Question("제목1", "내용1"));
        Question question2 = questions.save(new Question("제목2", "내용2"));
        Question question3 = questions.save(new Question("제목3", "내용3"));
        List<Question> expected = List.of(question1, question2, question3);
        synchronize();

        List<Question> actual = questions.findByDeletedFalse();

        assertAll(
                () -> assertThat(actual).hasSize(expected.size()),
                () -> assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(expected)
        );
    }

    @DisplayName("삭제되지 않은 질문 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Question expected = questions.save(question);
        synchronize();

        Optional<Question> actual = questions.findByIdAndDeletedFalse(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("삭제된 질문 조회 시 조회되지 않음")
    @Test
    void findByIdAndDeletedTrue() {
        Question question = questions.save(QuestionRepositoryTest.question);
        question.delete();
        synchronize();

        Optional<Question> actual = questions.findByIdAndDeletedFalse(question.getId());

        assertThat(actual).isEmpty();
    }

    @DisplayName("질문 정보 수정")
    @Test
    void update() {
        Question expected = questions.save(question);
        Question updatedQuestion = new Question("제목 수정", "내용 수정");

        expected.update(updatedQuestion);
        synchronize();

        Optional<Question> actual = questions.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("질문 삭제")
    @Test
    void delete() {
        Question deletedQuestion = questions.save(question);
        synchronize();

        questions.delete(deletedQuestion);
        synchronize();

        assertThat(questions.findAll()).hasSize(0);
    }
}
