package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import qna.domain.deletehistory.ContentType;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.user.UserTest;

public class AnswerTest {

    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, "Answers Contents2");

    @Test
    void delete_메서드는_현재_데이터를_삭제된_상태로_변경() {
        Answer answer = new Answer(1L, UserTest.JAVAJIGI, "Answers Contents1");

        answer.delete();

        assertThat(answer.isDeleted()).isEqualTo(true);
    }

    @Test
    void delete_메서드는_현재_데이터에_대한_DeleteHistory_반환() {
        Answer answer = new Answer(1L, UserTest.JAVAJIGI, "Answers Contents1");

        DeleteHistory actual = answer.delete();
        DeleteHistory expected = new DeleteHistory(ContentType.ANSWER, 1L, UserTest.JAVAJIGI);

        assertThat(actual).isEqualTo(expected);
    }
}
