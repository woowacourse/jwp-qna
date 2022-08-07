package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.user.User;
import qna.domain.user.UserRepository;
import qna.exception.AlreadyDeletedException;
import qna.exception.CannotDeleteException;
import qna.annotation.DatabaseTest;

@SuppressWarnings("NonAsciiCharacters")
@DatabaseTest
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
    void validateDeletableBy_메서드에_생성자_이외의_사용자가_입력된_경우_예외발생() {
        User anotherUser = users.save(new User("kotlinjigi", "password", "jason", "jason@slipp.net"));

        assertThatThrownBy(() -> answer.validateDeletableBy(anotherUser))
                .isInstanceOf(CannotDeleteException.class);
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

    @Test
    void delete_메서드는_이미_삭제된_경우_예외발생() {
        answer.delete();

        assertThatThrownBy(() -> answer.delete())
                .isInstanceOf(AlreadyDeletedException.class);
    }
}
