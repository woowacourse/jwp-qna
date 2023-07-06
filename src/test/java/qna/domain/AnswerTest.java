package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
    @Autowired
    private AnswerRepository answers;

    @Test
    void save(){
        // when
        final Answer actual = answers.save(A1);

        //then
        assertThat(answers.findById(actual.getId()).get()).isEqualTo(actual);
    }

    @Test
    void findById() {
        // given
        final long expected1 = answers.save(A1).getId();
        final long expected2 = answers.save(A2).getId();

        // when
        final Answer actual1 = answers.findById(expected1).get();
        final Answer actual2 = answers.findById(expected2).get();

        // then
        assertAll(
                ()-> assertThat(actual1.getId()).isEqualTo(expected1),
                ()-> assertThat(actual2.getId()).isEqualTo(expected2)
        );
    }

    @Test
    void update() {
        // given
        final Answer expected = answers.save(A1);

        // when
        expected.setContents("123123");

        //then
        assertThat(answers.findById(expected.getId()).get().getContents()).isEqualTo("123123");
    }

    @Test
    void delete() {
        // given
        final Answer expected = answers.save(A1);

        // when
        answers.delete(expected);

        //then
        assertThat(answers.findById(expected.getId()).isPresent()).isFalse();
    }
}
