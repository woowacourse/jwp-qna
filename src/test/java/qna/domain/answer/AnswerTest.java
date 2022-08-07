package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.annotation.DatabaseTest;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.user.User;
import qna.domain.user.UserRepository;
import qna.exception.AlreadyDeletedException;
import qna.exception.CannotDeleteException;

@SuppressWarnings("NonAsciiCharacters")
@DatabaseTest
class AnswerTest {

    @Autowired
    private UserRepository users;

    private User user;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = users.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        Question newQuestion = new Question("title1", "contents1", user);
        answer = new Answer(user, newQuestion, "Answers Contents1");
    }

    @Test
    void deleteBy_메서드는_현재_데이터를_삭제된_상태로_변경() {
        answer.deleteBy(user);

        assertThat(answer.isDeleted()).isEqualTo(true);
    }

    @Test
    void deleteBy_메서드는_현재_데이터에_대한_DeleteHistory_반환() {
        DeleteHistory actual = answer.deleteBy(user);
        DeleteHistory expected = answer.toDeleteHistory();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteBy_메서드는_메서드에_생성자_이외의_사용자가_입력된_경우_예외발생() {
        User anotherUser = users.save(new User("kotlinjigi", "password", "jason", "jason@slipp.net"));

        assertThatThrownBy(() -> answer.deleteBy(anotherUser))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void deleteBy_메서드는_이미_삭제된_경우_예외발생() {
        answer.deleteBy(user);

        assertThatThrownBy(() -> answer.deleteBy(user))
                .isInstanceOf(AlreadyDeletedException.class);
    }
}
