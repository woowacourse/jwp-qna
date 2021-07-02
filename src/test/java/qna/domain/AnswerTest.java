package qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class AnswerTest {
    public static final Answer A1 = new Answer(Date.valueOf(LocalDate.now()), UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(Date.valueOf(LocalDate.now()), UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("answer을 jpa를 이용해 저장한다.")
    void persist() {
        testEntityManager.persist(A1);
        assertThat(A1).isEqualTo(testEntityManager.find(Answer.class, A1.getId()));
    }
}
