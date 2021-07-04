package qna.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class AnswerTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("답변의 주인을 확인한다.")
    void checkOwner() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");   //TODO: 분리
        entityManager.persist(user);

        Question question = new Question("test", "test");
        entityManager.persist(question);

        Answer answer = new Answer(user, question, "Answers Contents1");
        entityManager.persist(answer);

        assertThat(entityManager.find(Answer.class, answer.getId()).isOwner(user)).isTrue();
    }

    @Test
    @DisplayName("답변을 삭제하고 삭제되었는지 확인한다.")
    void checkDeleted() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        entityManager.persist(user);

        Question question = new Question("test", "test");
        entityManager.persist(question);

        Answer answer = new Answer(user, question, "Answers Contents1");
        entityManager.persist(answer);

        answer.setDeleted(true);
        assertThat(entityManager.find(Answer.class, answer.getId()).isDeleted()).isTrue();
    }

    @AfterEach
    void afterEach() {
        entityManager.flush();
        entityManager.clear();
    }
}
