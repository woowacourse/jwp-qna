package qna.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    AnswerRepository answers;
    QuestionRepository questions;
    UserRepository users;

    AnswerTest(AnswerRepository answers, QuestionRepository questions, UserRepository users) {
        this.answers = answers;
        this.questions = questions;
        this.users = users;
    }

    @BeforeEach
    void setup() {
        UserTest.JAVAJIGI.setId(null);
        UserTest.SANJIGI.setId(null);
        QuestionTest.Q1.setId(null);
        QuestionTest.Q2.setId(null);

        users.save(UserTest.JAVAJIGI);
        users.save(UserTest.SANJIGI);
        questions.save(QuestionTest.Q1);
        questions.save(QuestionTest.Q2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer savedA1 = answers.save(A1);
        Answer savedA2 = answers.save(A2);
        savedA1.setDeleted(true);
        answers.flush();

        Optional<Answer> maybeA1 = answers.findByIdAndDeletedFalse(savedA1.getId());
        Optional<Answer> maybeA2 = answers.findByIdAndDeletedFalse(savedA2.getId());

        assertAll(
                () -> assertThat(maybeA1).isEmpty(),
                () -> assertThat(maybeA2).isPresent()
        );
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer savedA1 = answers.save(A1);
        Answer savedA2 = answers.save(A2);
        savedA1.setDeleted(true);
        answers.flush();

        List<Answer> answersByQuestionIdAndDeletedFalse = answers.findByQuestionIdAndDeletedFalse(QuestionTest.Q1.getId());

        assertThat(answersByQuestionIdAndDeletedFalse).hasSize(1);
    }

    @Test
    void save() {
        Answer saved = answers.save(A1);
        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getContents()).isEqualTo(A1.getContents())
        );
    }

    @Test
    void findById() {
        Answer saved = answers.save(A1);
        Answer expected = answers.findById(saved.getId()).get();

        assertThat(expected).isEqualTo(saved);
    }

    @Test
    void saveToUpdate() {
        Answer saved = answers.save(A1);
        answers.flush();

        Answer answer = answers.findById(saved.getId()).get();
        answer.setContents("수정된 내용");
        Answer updated = answers.save(saved);
        answers.flush(); // update 쿼리문 육안으로 확인

        Answer exptected = answers.findById(updated.getId()).get();
        assertThat(exptected.getContents()).isEqualTo(answer.getContents());
    }

    @Test
    void delete() {
        Answer saved = answers.save(A1);
        answers.flush();

        answers.delete(saved);
        answers.flush(); // delete 쿼리문 육안으로 확인

        Optional<Answer> maybe = answers.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
