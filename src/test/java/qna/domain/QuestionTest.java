package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    @Autowired
    private QuestionRepository questions;

    @Test
    void save() {
        // when
        final Question expected = questions.save(Q1);

        //then
        assertThat(questions.findById(expected.getId()).get()).isEqualTo(expected);
    }

    @Test
    void update() {
        // given
        final Question expected = questions.save(Q1);

        // when
        expected.setContents("123123");

        //then
        assertThat(questions.findById(expected.getId()).get().getContents()).isEqualTo("123123");
    }

    @Test
    void find() {
        // given
        final Question question1 = questions.save(Q1);
        final Question question2 = questions.save(Q2);

        // when
        final Question actual1 = questions.findById(question1.getId()).get();
        final Question actual2 = questions.findById(question2.getId()).get();

        //then
        assertAll(
                () -> assertThat(actual1).isEqualTo(question1),
                () -> assertThat(actual2).isEqualTo(question2)
        );
    }

    @Test
    void delete() {
        // given
        final Question expected = questions.save(Q1);

        // when
        questions.delete(expected);

        //then
        assertThat(questions.findById(expected.getId()).isPresent()).isFalse();
    }
}
