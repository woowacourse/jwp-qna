package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

public class QuestionTest {
    public static Question Q1() {
        return new Question("title1", "contents1", UserTest.JAVAJIGI);
    }

    @Test
    void 삭제시_자신의_질문이_아니면_예외() {
        // given
        Question question = Q1();

        // when & then
        assertThatThrownBy(() -> question.delete(SANJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제시_다른_사람의_답변이_있으면_예외() {
        //given
        Question question = Q1();
        question.addAnswer(new Answer(SANJIGI, question, "contents"));

        //when & then
        assertThatThrownBy(() -> question.delete(JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 자신의_질문_삭제() throws CannotDeleteException {
        //given
        Question question = Q1();
        question.addAnswer(new Answer(JAVAJIGI, question, "contents"));

        // when
        question.delete(JAVAJIGI);

        //when & then
        assertAll(
                () -> assertThat(question.isDeleted()).isTrue(),
                () -> assertThat(question.getAnswers()).allMatch(Answer::isDeleted)
        );
    }

}
