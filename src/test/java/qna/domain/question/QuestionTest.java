package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import qna.domain.answer.AnswerTest;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.user.UserTest;

public class QuestionTest {

    public static final Question Q1 = new Question("title1", "contents1", UserTest.JAVAJIGI);

    static {
        Q1.addAnswer(AnswerTest.A1);
        Q1.addAnswer(AnswerTest.A2);
    }

    @Test
    void delete_메서드는_현재_데이터를_삭제된_상태로_변경() {
        Question question = new Question("title2", "contents2", UserTest.SANJIGI);

        question.delete();

        assertThat(question.isDeleted()).isEqualTo(true);
    }

    @Test
    void delete_메서드는_현재_데이터_자체와_연관된_데이터에_대한_DeleteHistory_리스트_반환() {
        Question question = new Question("title2", "contents2", UserTest.SANJIGI);
        question.addAnswer(AnswerTest.A1);
        question.addAnswer(AnswerTest.A2);

        List<DeleteHistory> actual = question.delete();
        List<DeleteHistory> expected = List.of(
                DeleteHistory.of(question),
                DeleteHistory.of(AnswerTest.A1),
                DeleteHistory.of(AnswerTest.A2));

        assertThat(actual).isEqualTo(expected);
    }
}
