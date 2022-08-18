package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.fixtures.UserFixture;

public class AnswerTest {

    @DisplayName("답변 삭제 시 삭제되고 삭제 이력 반환")
    @Test
    void delete() {
        User user = UserFixture.JAVAJIGI.generate(1L);
        Question question = new Question(1L, "질문입니다", "질문의 내용입니다");
        Answer answer = new Answer(1L, user, question, "답변입니다");
        DeleteHistory expected = new DeleteHistory(ContentType.ANSWER, answer.getId(), user);

        DeleteHistory actual = answer.deleteBy(user);

        assertAll(
                () -> assertThat(answer.isDeleted()).isTrue(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("타인이 답변 삭제 시도 시 예외 발생")
    @Test
    void deleteByOtherUser_throwsException() {
        User other = UserFixture.SANJIGI.generate(1L);
        Question question = new Question(1L, "질문입니다", "질문의 내용입니다");

        User user = UserFixture.JAVAJIGI.generate(2L);
        Answer answer = new Answer(1L, user, question, "답변입니다");

        assertThatThrownBy(() -> answer.deleteBy(other));
    }
}
