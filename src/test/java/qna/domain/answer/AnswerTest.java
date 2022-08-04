package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerTest {

    @Autowired
    private UserRepository users;

    private Answer answer;

    @BeforeEach
    void setUp() {
        User user = users.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        Question newQuestion = new Question("title1", "contents1", user);
        answer = new Answer(user, newQuestion, "Answers Contents1");
    }

    @Test
    void delete_메서드는_현재_데이터를_삭제된_상태로_변경() {
        answer.delete();

        assertThat(answer.isDeleted()).isEqualTo(true);
    }

    @Test
    void delete_메서드는_현재_데이터에_대한_DeleteHistory_반환() {
        DeleteHistory actual = answer.delete();
        DeleteHistory expected = answer.toDeleteHistory();

        assertThat(actual).isEqualTo(expected);
    }
}
