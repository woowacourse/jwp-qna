package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import org.junit.jupiter.api.Test;
import qna.NotFoundException;
import qna.UnAuthorizedException;

public class AnswerTest {
    public static final Answer A1 = new Answer(JAVAJIGI, Q1(), "Answers Contents1");
    public static final Answer A2 = new Answer(SANJIGI, Q1(), "Answers Contents2");

    @Test
    void 작성자가_null_이면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new Answer(null, Q1(), "contents"))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    void 질문이_null_이면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new Answer(JAVAJIGI, null, "contents"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void 작성자_확인시_같으면_참() {
        // given
        Answer answer = new Answer(JAVAJIGI, Q1(), "contents");

        // when & then
        assertThat(answer.isOwner(JAVAJIGI)).isTrue();
    }
}
