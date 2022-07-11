package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionTest {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @BeforeEach
    void setup() {
        User user = users.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        questions.saveAndFlush(new Question("title1", "contents1", user));
    }

    @Test
    void getWriterId_메서드처럼_내부적으로_null인_연관관계를_호출하려는_경우에도_SELECT문_실행하여_지연로딩_발생() {
        List<Question> allQuestions = questions.findAll();
        Question question = allQuestions.get(0);

        Long writerId = question.getWriterId();
        assertThat(writerId).isEqualTo(1L);
    }
}
