package qna.domain;

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
public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    UserRepository users;
    QuestionRepository questions;

    QuestionTest(QuestionRepository questions, UserRepository users) {
        this.questions = questions;
        this.users = users;
    }

    @BeforeEach
    void setup() {
        UserTest.JAVAJIGI.setId(null);
        UserTest.SANJIGI.setId(null);

        users.save(UserTest.JAVAJIGI);
        users.save(UserTest.SANJIGI);
    }

    @Test
    void findByIdAndDeletedFalse() {
        Question savedQ1 = questions.save(Q1);
        Question savedQ2 = questions.save(Q2);
        savedQ1.setDeleted(true);
        questions.flush();

        Optional<Question> maybeQ1 = questions.findByIdAndDeletedFalse(savedQ1.getId());
        Optional<Question> maybeQ2 = questions.findByIdAndDeletedFalse(savedQ2.getId());

        assertAll(
                () -> assertThat(maybeQ1).isEmpty(),
                () -> assertThat(maybeQ2).isPresent()
        );
    }

    @Test
    void findByDeletedFalse() {
        Question savedQ1 = questions.save(Q1);
        Question savedQ2 = questions.save(Q2);
        savedQ1.setDeleted(true);
        questions.flush();

        List<Question> questionsByDeletedFalse = questions.findByDeletedFalse();

        assertThat(questionsByDeletedFalse).hasSize(1);
    }

    @Test
    void save() {
        Question saved = questions.save(Q1);
        assertAll(
                () -> assertThat(saved.getId()).isNotNull(),
                () -> assertThat(saved.getContents()).isEqualTo(Q1.getContents())
        );
    }

    @Test
    void findById() {
        Question saved = questions.save(Q1);
        Question expected = questions.findById(saved.getId()).get();

        assertThat(expected).isEqualTo(saved);
    }

    @Test
    void saveToUpdate() {
        Question saved = questions.save(Q1);
        questions.flush();

        Question question = questions.findById(saved.getId()).get();
        question.setContents("수정된 내용");
        Question updated = questions.save(saved);
        questions.flush(); // update 쿼리문 육안으로 확인

        Question exptected = questions.findById(updated.getId()).get();
        assertThat(exptected.getContents()).isEqualTo(question.getContents());
    }

    @Test
    void delete() {
        Question saved = questions.save(Q1);
        questions.flush();

        questions.delete(saved);
        questions.flush(); // delete 쿼리문 육안으로 확인

        Optional<Question> maybe = questions.findById(saved.getId());
        assertThat(maybe).isEmpty();
    }
}
