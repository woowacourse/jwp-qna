package qna.domain.answer;

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
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private UserRepository users;

    @Autowired
    private QuestionRepository questions;

    @Test
    @DisplayName("answer를 저장한다.")
    void save() {
        User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        Question question = new Question("title1", "contents1").writeBy(user);
        Answer answer = new Answer(user, question, "Answers Contents1");

        users.save(user);
        questions.save(question);
        answers.save(answer);

        assertThat(answers.findAll()).containsExactly(answer);
    }
}