package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {

    @DisplayName("답변 삭제 시 삭제되고 삭제 이력 반환")
    @Test
    void delete() {
        User user = new User(1L, "user", "password", "사용자", "user@gmail.com ");
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
        User other = new User(1L, "other", "password", "질문자", "other@gmail.com ");
        Question question = new Question(1L, "질문입니다", "질문의 내용입니다");

        User user = new User(2L, "user", "password", "답변자", "user@gmail.com");
        Answer answer = new Answer(1L, user, question, "답변입니다");

        assertThatThrownBy(() -> answer.deleteBy(other));
    }
}
