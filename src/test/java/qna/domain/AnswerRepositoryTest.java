package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AnswerRepositoryTest extends CashManager {

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    private static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    private static final Question QUESTION = new Question("제목", "내용");
    private static final Answer ANSWER = new Answer(JAVAJIGI, QUESTION, "답변");

    private static Question SAVED_QUESTION;
    private static User SAVED_JAVAJIGI;

    @BeforeEach
    void setUp() {
        SAVED_QUESTION = questions.save(QUESTION);
        ANSWER.toQuestion(SAVED_QUESTION);

        SAVED_JAVAJIGI = users.save(JAVAJIGI);
        ANSWER.setWriter(SAVED_JAVAJIGI);
    }

    @DisplayName("답변 생성")
    @Test
    void save() {
        Answer expected = ANSWER;

        Answer actual = answers.save(expected);

        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("createDate")
                .ignoringFields("updateDate")
                .isEqualTo(expected);
    }

    @DisplayName("답변 조회")
    @Test
    void findById() {
        Answer expected = answers.save(ANSWER);
        synchronize();

        Optional<Answer> actual = answers.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("삭제되지 않은 답변 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Answer expected = answers.save(ANSWER);
        synchronize();

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("질문에 속하는 답변 목록 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer1 = answers.save(new Answer(SAVED_JAVAJIGI, SAVED_QUESTION, "답변1"));
        Answer answer2 = answers.save(new Answer(SAVED_JAVAJIGI, SAVED_QUESTION, "답변2"));
        Answer answer3 = answers.save(new Answer(SAVED_JAVAJIGI, SAVED_QUESTION, "답변3"));
        List<Answer> expected = List.of(answer1, answer2, answer3);
        synchronize();

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(SAVED_QUESTION.getId());

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @DisplayName("답변 수정")
    @Test
    void update() {
        Answer answer = answers.save(ANSWER);
        Answer expected = new Answer(SAVED_JAVAJIGI, SAVED_QUESTION, "답변 수정");

        answer.update(expected);
        synchronize();

        Optional<Answer> actual = answers.findById(answer.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("createDate")
                .ignoringFields("updateDate")
                .isEqualTo(expected);
    }

    @DisplayName("답변 삭제")
    @Test
    void delete() {
        Answer answer = answers.save(ANSWER);
        synchronize();

        answers.delete(answer);
        synchronize();

        assertThat(answers.findAll()).hasSize(0);
    }
}
