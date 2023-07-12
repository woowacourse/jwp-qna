package qna.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixture.Fixture.Q1;
import static qna.fixture.Fixture.SANJIGI;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class QuestionTest {

    @Test
    void 질문자는_질문을_삭제할_수_있다() {
        // given
        final User writer = Q1.getWriter();

        // when
        Q1.deleteBy(writer);

        // then
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    void 질문자가_삭제하지_않을_경우_예외가_발생한다() {
        // given
        final User writer = SANJIGI;

        // expect
        assertThatThrownBy(() -> Q1.deleteBy(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }
}
