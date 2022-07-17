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

    private static final Question QUESTION = new Question("제목", "내용");

    @DisplayName("질문 생성")
    @Test
    void save() {
        Question expected = QUESTION;

        Question actual = questions.save(expected);

        assertAll(
                () -> assertThat(actual.getTitle()).isEqualTo(expected.getTitle()),
                () -> assertThat(actual.getContents()).isEqualTo(expected.getContents())
        );
    }

    @DisplayName("질문 조회")
    @Test
    void findById() {
        Question expected = questions.save(QUESTION);

        Optional<Question> actual = questions.findById(expected.getId());

        assertThat(actual).hasValue(expected);
    }

    @DisplayName("삭제되지 않은 질문 목록 조회")
    @Test
    void findByDeletedFalse() {
        questions.save(new Question("제목1", "내용1"));
        questions.save(new Question("제목2", "내용2"));
        questions.save(new Question("제목3", "내용3"));

        List<Question> questionsDeletedFalse = questions.findByDeletedFalse();

        assertThat(questionsDeletedFalse).hasSize(3);
    }

    @DisplayName("삭제되지 않은 질문 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Question question = questions.save(QUESTION);

        Optional<Question> actual = questions.findByIdAndDeletedFalse(question.getId());

        assertThat(actual).isPresent();
    }

    @DisplayName("삭제된 질문 조회 시 조회되지 않음")
    @Test
    void findByIdAndDeletedTrue() {
        Question question = questions.save(QUESTION);
        question.delete();

        Optional<Question> actual = questions.findByIdAndDeletedFalse(question.getId());

        assertThat(actual).isEmpty();
    }

    @DisplayName("질문 정보 수정")
    @Test
    void update() {
        Question question = questions.save(QUESTION);
        Question updatedQuestion = new Question("제목 수정", "내용 수정");

        question.update(updatedQuestion);

        assertAll(
                () -> assertThat(question.getTitle()).isEqualTo(updatedQuestion.getTitle()),
                () -> assertThat(question.getContents()).isEqualTo(updatedQuestion.getContents())
        );
    }

    @DisplayName("질문 삭제")
    @Test
    void delete() {
        Question question = questions.save(QUESTION);

        questions.delete(question);

        assertThat(questions.findAll()).hasSize(0);
    }
}
