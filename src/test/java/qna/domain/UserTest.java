package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.Fixture.A1;
import static qna.fixture.Fixture.JAVAJIGI;
import static qna.fixture.Fixture.Q1;

@DataJpaTest
public class UserTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void addAnswer() {
        final User user = userRepository.save(JAVAJIGI);

        user.addAnswer(A1);
        em.flush();
        em.clear();

        final User actual = userRepository.findById(user.getId()).get();
        assertThat(actual.getAnswers()).hasSize(1);
    }

    @Test
    void addQuestion() {
        final User user = userRepository.save(JAVAJIGI);

        user.addQuestion(Q1);
        em.flush();
        em.clear();

        final User actual = userRepository.findById(user.getId()).get();
        assertThat(actual.getQuestions()).hasSize(1);
    }

    @Test
    void addDeleteHistory() {
        final User user = userRepository.save(JAVAJIGI);
        final Question question = questionRepository.save(Q1);
        final DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, question.getId());

        user.addDeleteHistory(deleteHistory);
        em.flush();
        em.clear();

        final User actual = userRepository.findById(user.getId()).get();
        assertThat(actual.getDeleteHistories()).hasSize(1);
    }
}
