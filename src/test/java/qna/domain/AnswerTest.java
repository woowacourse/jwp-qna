package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixtures.QuestionFixture.Q1;
import static qna.fixtures.UserFixture.JAVAJIGI;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnswerTest {

    @Test
    void 소유자인지_확인한다() {
        // given
        Answer answer = new Answer(JAVAJIGI, Q1, "안녕하세요");

        // expect
        assertThat(answer.isOwner(JAVAJIGI)).isTrue();
    }

    @Test
    void 답변에_대한_질문을_설정한다() {
        // given
        Question question = new Question(1L, "질문1", "안녕하신가요?");
        Answer answer = new Answer(JAVAJIGI, Q1, "안녕하세요");

        // when
        answer.toQuestion(question);

        // then
        assertThat(answer.getQuestion()).isEqualTo(question);
    }

    @Test
    void 삭제된_상태로_변경한다() {
        // given
        Answer answer = new Answer(JAVAJIGI, Q1, "안녕하세요");

        // when
        answer.delete();

        // then
        assertThat(answer.isDeleted()).isTrue();
    }

    @Test
    void 이미_삭제된_답변을_삭제할_경우_예외가_발생한다() {
        // given
        Answer answer = new Answer(JAVAJIGI, Q1, "안녕하세요");
        answer.delete();

        // expect
        assertThatThrownBy(() -> answer.delete())
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("이미 삭제된 답변입니다.");
    }
}
