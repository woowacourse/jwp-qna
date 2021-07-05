package qna.domain.answer;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import qna.domain.ContentType;
import qna.domain.answer.Answer;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.user.User;

public class AnswerTest {

    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        user = new User("dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        question = new Question("test", "test");
        answer = new Answer(user, question, "Answers Contents1");
    }

    @Test
    @DisplayName("답변의 주인을 확인한다.")
    void checkOwner() {
        assertThat(answer.isOwner(user)).isTrue();
    }

    @Test
    @DisplayName("답변을 삭제하고 삭제되었는지 확인한다.")
    void checkDeleted() {
        answer.setDeleted(true);

        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("답변을 삭제하고 삭제 이력을 반환한다.")
    void delete() {
        DeleteHistory deleteHistory = answer.delete();

        assertThat(deleteHistory).isEqualTo(
            new DeleteHistory(
                ContentType.ANSWER,
                answer.getId(),
                answer.getWriter(),
                LocalDateTime.now())
        );
    }
}
