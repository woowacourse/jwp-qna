package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.fixtures.AnswerFixture.A1;
import static qna.fixtures.QuestionFixture.Q1;
import static qna.fixtures.UserFixture.JAVAJIGI;
import static qna.fixtures.UserFixture.SANJIGI;

public class AnswerTest {

    @DisplayName("답변의 소유자를 확인할 수 있다.")
    @Test
    void isOwner() {

        // when, then
        assertAll(
                () -> assertThat(A1.isOwner(JAVAJIGI)).isTrue(),
                () -> assertThat(A1.isOwner(SANJIGI)).isFalse()
        );
    }

    @DisplayName("Question에 Answer를 추가할 수 있다.")
    @Test
    void toQuestion() {

        // when
        Q1.addAnswer(A1);

        // then
        assertThat(A1.getQuestionId()).isEqualTo(Q1.getId());
    }
}
