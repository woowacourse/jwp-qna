package qna.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixture.Fixture.A1;
import static qna.fixture.Fixture.SANJIGI;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AnswerTest {

    @Test
    void 답변자는_답변을_삭제할_수_있다() {
        // given
        final User writer = A1.getWriter();

        // when
        A1.deleteBy(writer);

        // then
        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    void 답변자가_아닌_사람이_답변을_삭제할_경우_예외가_발생한다() {
        // given
        final User writer = SANJIGI;

        // expect
        assertThatThrownBy(() -> A1.deleteBy(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("답변을 삭제할 권한이 없습니다.");
    }
}
