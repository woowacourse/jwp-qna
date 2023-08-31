package qna.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;
import qna.exception.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixture.Fixture.JAVAJIGI;
import static qna.fixture.Fixture.Q1;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AnswerTest {

    @Test
    void 답변자는_답변을_삭제할_수_있다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu,"내용");
        final Answer answer = new Answer(gugu, question, "레벨 4 강의는 제가합니다");

        // when
        answer.deleteBy(gugu);

        // then
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    void 답변자가_아닌_사람이_답변을_삭제할_경우_예외가_발생한다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu, "내용");
        final Answer answer = new Answer(gugu, question, "답변내용");

        // expect
        assertThatThrownBy(() -> answer.deleteBy(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("답변을 삭제할 권한이 없습니다.");
    }

    @Test
    void 답변_생성시_작성자가_없다면_예외가_발생한다() {
        // given
        final Question question = Q1;

        // expect
        assertThatThrownBy(() -> new Answer(null, question, "내용"))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void 답변_생성시_질문자가_없다면_예외가_발생한다() {
        // given
        final User user = JAVAJIGI;

        // expect
        assertThatThrownBy(() -> new Answer(user, null, "내용"))
                .isInstanceOf(NotFoundException.class);
    }
}
