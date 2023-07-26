package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixtures.UserFixture.JAVAJIGI;
import static qna.fixtures.UserFixture.SANJIGI;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class QuestionTest {

    @Test
    void 작성자를_설정한다() {
        // given
        Question question = new Question("질문", "내용");

        // when
        question.writeBy(JAVAJIGI);

        // then
        assertThat(question.getWriter()).isEqualTo(JAVAJIGI);
    }

    @Test
    void 소유자를_확인한다() {
        // given
        Question question = new Question("질문", "내용");
        question.writeBy(JAVAJIGI);

        // expect
        assertThat(question.isOwner(JAVAJIGI)).isTrue();
    }

    @Test
    void 작성자가_질문을_삭제한다() {
        // given
        Question question = new Question("질문", "내용");
        question.writeBy(JAVAJIGI);

        // when
        question.deleteBy(JAVAJIGI);

        // then
        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    void 이미_삭제된_질문을_삭제할_경우_예외가_발생한다() {
        // given
        Question question = new Question("질문", "내용");
        question.writeBy(JAVAJIGI);
        question.deleteBy(JAVAJIGI);

        // expect
        assertThatThrownBy(() -> question.deleteBy(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("이미 삭제된 질문입니다.");
    }

    @Test
    void 질문의_작성자가_아니면_질문을_삭제할_수_없다() {
        // given
        Question question = new Question("질문", "내용");
        question.writeBy(JAVAJIGI);

        // expect
        assertThatThrownBy(() -> question.deleteBy(SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void 작성자_이외의_질문_답변자가_있으면_질문을_삭제할_수_없다() {
        // given
        Question question = new Question("질문", "내용");
        question.writeBy(JAVAJIGI);
        Answer answer = new Answer(SANJIGI, question, "answer contents");
        question.addAnswer(answer);

        // expect
        assertThatThrownBy(() -> question.deleteBy(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
