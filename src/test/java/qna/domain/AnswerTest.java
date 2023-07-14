package qna.domain;

import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static qna.domain.Fixture.*;

class AnswerTest {

    @Test
    void 답변을_삭제한다() {
        User user = userFixtureJavajigi();
        Answer answer = new Answer(user, questionFixture(user), "답변입니다");

        answer.deleteBy(user);

        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    void 답변_삭제에_실패한다() {
        User user = userFixtureJavajigi();
        Answer answer = new Answer(userFixtureSanjigi(), questionFixture(user), "답변입니다");

        assertThatThrownBy(() -> answer.deleteBy(user))
                .isInstanceOf(CannotDeleteException.class);
    }
}
