package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.NotFoundException;
import qna.UnAuthorizedException;
import qna.fixture.AnswerFixture;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {

    @Test
    @DisplayName("사용자가 지정되지 않으면 예외가 발생한다.")
    void failWhenUnAuthorizedWriter() {
        // given
        final User writer = null;
        final Question question = QuestionFixture.Q1();

        // when & then
        assertThatThrownBy(() -> new Answer(1L, writer, question, "test"))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("질문이 지정되지 않으면 예외가 발생한다.")
    void failWhenQuestionNotFound() {
        // given
        final User writer = UserFixture.JAVAJIGI();
        final Question question = null;

        // when & then
        assertThatThrownBy(() -> new Answer(1L, writer, question, "test"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("답변한 사용자가 맞음을 확인한다.")
    void isOwnerTrue() {
        // given
        final Answer answer = AnswerFixture.A1();

        // when
        final boolean actual = answer.isOwner(UserFixture.JAVAJIGI());

        //then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("답변한 사용자가 아님을 확인한다.")
    void isOwnerFalse() {
        // given
        final Answer answer = AnswerFixture.A1();

        // when
        final boolean actual = answer.isOwner(UserFixture.SANJIGI());

        //then
        assertThat(actual).isFalse();
    }
}
