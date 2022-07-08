package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @DisplayName("삭제되지 않은 모든 Question 조회")
    @Test
    void findByDeletedFalse() {
        Question expectIncluded = new Question("질문의 제목입니다.", "질문의 내용입니다.");
        questions.save(expectIncluded);

        Question expectNotIncluded = new Question("질문의 제목입니다.", "질문의 내용입니다.");
        expectNotIncluded.setDeleted(true);
        questions.save(expectNotIncluded);

        List<Question> actual = questions.findByDeletedFalse();

        assertAll(
                () -> assertThat(actual.contains(expectIncluded)).isTrue(),
                () -> assertThat(actual.contains(expectNotIncluded)).isFalse()
        );
    }

    @DisplayName("id가 일치하고 삭제되지 않은 Question을 조회하고, 값이 존재")
    @Test
    void findByIdAndDeletedFalse_resultExist() {
        Question expect = new Question("질문의 제목입니다.", "질문의 내용입니다.");
        Question saved = questions.save(expect);

        Optional<Question> actual = questions.findByIdAndDeletedFalse(saved.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expect)
        );
    }

    @DisplayName("id가 일치하고 삭제되지 않은 Question을 조회하고, 값이 존재하지 않음")
    @Test
    void findByIdAndDeletedFalse_resultDoesNotExist() {
        Question expect = new Question("질문의 제목입니다.", "질문의 내용입니다.");
        expect.setDeleted(true);
        Question saved = questions.save(expect);

        Optional<Question> actual = questions.findByIdAndDeletedFalse(saved.getId());

        assertThat(actual).isEmpty();
    }
}