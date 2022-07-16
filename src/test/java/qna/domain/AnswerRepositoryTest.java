package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private QuestionRepository questions;

    private static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    private static final Question QUESTION = new Question("제목", "내용");
    private static final Answer ANSWER = new Answer(JAVAJIGI, QUESTION, "답변");
    private static Question SAVED_QUESTION;

    @BeforeEach
    void setUp() {
        SAVED_QUESTION = questions.save(QUESTION);
        ANSWER.toQuestion(SAVED_QUESTION);
    }

    @DisplayName("답변 생성")
    @Test
    void save() {
        Answer expected = ANSWER;

        Answer actual = answers.save(expected);

        assertAll(
                () -> assertThat(actual.getWriterId()).isEqualTo(JAVAJIGI.getId()),
                () -> assertThat(actual.getQuestion().getTitle()).isEqualTo(SAVED_QUESTION.getTitle()),
                () -> assertThat(actual.getQuestion().getContents()).isEqualTo(SAVED_QUESTION.getContents()),
                () -> assertThat(actual.getContents()).isEqualTo(expected.getContents())
        );
    }

    @DisplayName("답변 조회")
    @Test
    void findById() {
        Answer expected = answers.save(ANSWER);

        Optional<Answer> actual = answers.findById(expected.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }

    @DisplayName("삭제되지 않은 답변 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Answer expected = answers.save(ANSWER);

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(expected.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }

    @DisplayName("질문에 속하는 답변 목록 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer = answers.save(ANSWER);

        List<Answer> answersDeletedFalse = answers.findByQuestionIdAndDeletedFalse(SAVED_QUESTION.getId());

        assertThat(answersDeletedFalse).contains(answer);
    }

    @DisplayName("답변 수정")
    @Test
    void update() {
        Answer answer = answers.save(ANSWER);
        Answer updatedAnswer = new Answer(JAVAJIGI, SAVED_QUESTION, "답변 수정");

        answer.update(updatedAnswer);

        assertThat(answer.getContents()).isEqualTo(updatedAnswer.getContents());
    }

    @DisplayName("답변 삭제")
    @Test
    void delete() {
        Answer answer = answers.save(ANSWER);

        answers.delete(answer);

        assertThat(answers.findAll()).hasSize(0);
    }
}
