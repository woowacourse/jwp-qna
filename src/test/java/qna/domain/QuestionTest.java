package qna.domain;

import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static qna.domain.Fixture.questionFixture;
import static qna.domain.Fixture.userFixtureJavajigi;
import static qna.domain.Fixture.userFixtureSanjigi;

class QuestionTest {


    @Test
    void 질문을_삭제한다() {
        User user = userFixtureJavajigi();
        Question question = questionFixture(user);

        question.deleteBy(user, of());

        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    void 질문을_삭제에_실패한다() {
        User user = userFixtureJavajigi();
        Question question = questionFixture(user);
        Answer answer = new Answer(userFixtureSanjigi(), question, "답변입니다");

        assertThatThrownBy(() -> question.deleteBy(user, of(answer)))
                .isInstanceOf(CannotDeleteException.class);
    }
}
