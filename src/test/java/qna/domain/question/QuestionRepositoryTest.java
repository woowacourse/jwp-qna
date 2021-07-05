package qna.domain.question;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import qna.domain.question.Question;
import qna.domain.question.QuestionRepository;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @Test
    @DisplayName("Question을 저장한다.")
    void save() {
        User user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        users.save(user);

        Question question = new Question("title", "content");
        question.writeBy(user);
        questions.save(question);

        assertThat(questions.findById(question.getId()).get()).isEqualTo(question);
    }
}