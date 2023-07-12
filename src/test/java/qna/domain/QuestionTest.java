package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixtures.UserFixture.JAVAJIGI;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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
    void 삭제된_상태로_변경한다() {
        // given
        Question question = new Question("질문", "내용");

        // when
        question.changeDeleted(true);

        // then
        assertThat(question.isDeleted()).isTrue();
    }
}
