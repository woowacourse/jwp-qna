package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixture.Fixture.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class QuestionTest {

    @Test
    void 질문자는_질문을_삭제할_수_있다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu, "내용");

        // when
        question.deleteBy(gugu);

        // then
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    void 질문_작성자가_아닌_다른이가_삭제할_경우_예외가_발생한다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu, "내용");

        // expect
        assertThatThrownBy(() -> question.deleteBy(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void 질문자와_답변자들이_동일하면_삭제가_가능하다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu, "내용");
        question.addAnswer(gugu, question, "레벨 4 강의는 제가 합니다");

        // when
        question.deleteBy(gugu);

        // then
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    void 질문자가_아닌_사람이_남긴_댓글이_있으면_삭제시_예외가_발생한다() {
        // given
        final User gugu = new User("gugu", "password", "구구", "gugu@gugu.com");
        final Question question = new Question("제목", gugu, "내용");
        question.addAnswer(JAVAJIGI, question, "여자친구는 2주에 한 번 만나세요");

        // expect
        assertThatThrownBy(() -> question.deleteBy(gugu))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("답변을 삭제할 권한이 없습니다");
    }
}
