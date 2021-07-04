package qna.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class QuestionTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("질문의 주인을 설정하고 확인한다.")
    void persist() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");   //TODO: 분리
        entityManager.persist(user);

        Question question = new Question("test", "test").writeBy(user);
        entityManager.persist(question);

        assertThat(question.isOwner(user)).isTrue();
        assertThat(entityManager.find(Question.class, question.getId()).isOwner(user)).isTrue();
    }

    @Test
    @DisplayName("질문에 답변을 추가한다.")
    void checkOwner() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        entityManager.persist(user);

        Question question = new Question("test", "test").writeBy(user);
        entityManager.persist(question);

        Answer answer = new Answer(user, question, "content");
        entityManager.persist(answer);
        question.addAnswer(answer);

        assertThat(entityManager.find(Question.class, question.getId()).getAnswers()).containsExactly(answer);
    }

    @Test
    @DisplayName("질문을 삭제하고 삭제되었는지 확인한다.")
    void checkDeleted() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        entityManager.persist(user);

        Question question = new Question("test", "test");
        entityManager.persist(question);

        question.setDeleted(true);
        assertThat(entityManager.find(Question.class, question.getId()).isDeleted()).isTrue();
    }
}
