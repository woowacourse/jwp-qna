package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;
import qna.fixtures.QuestionFixture;
import qna.fixtures.UserFixture;

class QuestionTest {

    @DisplayName("본인이 작성한 질문 삭제")
    @Test
    void deleteByWriter() {
        User writer = UserFixture.JAVAJIGI.generate(1L);
        Question question = QuestionFixture.FIRST.generate().writeBy(writer);

        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, question.getId(), writer);
        List<DeleteHistory> actual = question.deleteBy(writer);

        assertAll(
                () -> assertThat(question.isDeleted()).isTrue(),
                () -> assertThat(actual).containsExactly(expected)
        );
    }

    @DisplayName("다른 사람이 쓴 질문은 삭제 시 예외")
    @Test
    void deleteByNotWriter_throwsException() {
        User writer = UserFixture.JAVAJIGI.generate(1L);
        User other = UserFixture.SANJIGI.generate(2L);

        Question question = QuestionFixture.FIRST.generate().writeBy(writer);

        assertThatThrownBy(() -> question.deleteBy(other))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문은 작성자 본인만 삭제할 수 있습니다.");
    }

    @DisplayName("본인이 작성한 답변만 있다면 질문 삭제 가능")
    @Test
    void containsAnswerWrittenByWriter_deleteByWriter() {
        User writer = UserFixture.JAVAJIGI.generate(1L);
        Question question = QuestionFixture.FIRST.generate().writeBy(writer);
        Answer answer = new Answer(1L, writer, question, "작성자가 작성한 답변입니다.");
        question.addAnswer(answer);

        List<DeleteHistory> expected = Arrays.asList(
                new DeleteHistory(ContentType.ANSWER, answer.getId(), writer),
                new DeleteHistory(ContentType.QUESTION, question.getId(), writer)
        );
        List<DeleteHistory> actual = question.deleteBy(writer);

        assertAll(
                () -> assertThat(question.isDeleted()).isTrue(),
                () -> assertThat(actual).containsExactlyElementsOf(expected)
        );

    }

    @DisplayName("다른 사람이 쓴 답변이 존재하면 삭제 시 예외")
    @Test
    void containsAnswerWrittenByOther_throwsException() {
        User writer = UserFixture.JAVAJIGI.generate(1L);
        User other = UserFixture.SANJIGI.generate(2L);

        Question question = QuestionFixture.FIRST.generate().writeBy(writer);
        question.addAnswer(new Answer(other, question, "다른 사람이 쓴 답변입니다."));

        assertThatThrownBy(() -> question.deleteBy(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
    
    @DisplayName("다른 사람이 쓴 답변이 이미 삭제되었다면 삭제 가능")
    @Test
    void containsAnswerWrittenByOther_alreadyDeleted() {
        User writer = UserFixture.JAVAJIGI.generate(1L);
        User other = UserFixture.SANJIGI.generate(2L);

        Question question = QuestionFixture.FIRST.generate().writeBy(writer);
        Answer answer = new Answer(other, question, "다른 사람이 쓴 답변입니다.");
        answer.deleteBy(other);
        question.addAnswer(answer);

        DeleteHistory expected = new DeleteHistory(ContentType.QUESTION, question.getId(), writer);
        List<DeleteHistory> actual = question.deleteBy(writer);

        assertAll(
                () -> assertThat(question.isDeleted()).isTrue(),
                () -> assertThat(actual).containsExactly(expected)
        );
    }
}
