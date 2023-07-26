package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixtures.QuestionFixture.Q1;
import static qna.fixtures.UserFixture.JAVAJIGI;
import static qna.fixtures.UserFixture.SANJIGI;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AnswersTest {

    @Test
    void 답변_중_소유자가_아닌_사람이_있는_경우_예외가_발생한다() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, Q1, "안녕1"));
        answers.add(new Answer(SANJIGI, Q1, "안녕2"));

        // expect
        assertThatThrownBy(() -> answers.isAllAnswerOwner(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    void 답변_중_소유자가_아닌_사람이_없는_경우_예외가_발생하지_않는다() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, Q1, "안녕1"));

        // expect
        assertThatNoException().isThrownBy(() -> answers.isAllAnswerOwner(JAVAJIGI));
    }

    @Test
    void 답변을_추가한다() {
        // given
        Answers answers = new Answers();

        // when
        answers.add(new Answer(JAVAJIGI, Q1, "안녕"));

        // then
        assertThat(answers.getItems()).hasSize(1);
    }

    @Test
    void 답변을_삭제한다() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, Q1, "안녕"));

        // when
        List<DeleteHistory> deleteHistories = answers.delete();

        // then
        assertThat(deleteHistories).hasSize(1);
    }
}
