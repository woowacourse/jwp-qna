package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.fixture.QuestionFixture;
import qna.fixture.UserFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

    @Test
    @DisplayName("질문한 사용자가 맞음을 확인한다.")
    void isOwnerTrue() {
        // given
        final Question question = QuestionFixture.Q1();

        // when
        final boolean actual = question.isOwner(UserFixture.JAVAJIGI());

        //then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("질문한 사용자가 아님을 확인한다.")
    void isOwnerFalse() {
        // given
        final Question question = QuestionFixture.Q1();

        // when
        final boolean actual = question.isOwner(UserFixture.SANJIGI());

        //then
        assertThat(actual).isFalse();
    }
}
