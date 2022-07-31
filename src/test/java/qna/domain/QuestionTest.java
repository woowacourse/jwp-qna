package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

class QuestionTest {

    @DisplayName("본인이 작성한 질문 삭제")
    @Test
    void deleteByWriter() throws CannotDeleteException {
        User writer = new User(1L, "writer", "password", "작성자", "writer@gmail");
        Question question = new Question("게시글", "게시글 내용").writeBy(writer);

        assertThat(question.isDeleted()).isFalse();
        question.deleteBy(writer);

        assertThat(question.isDeleted()).isTrue();
    }

    @DisplayName("다른 사람이 쓴 질문은 삭제 시 예외")
    @Test
    void deleteByNotWriter_throwsException() {
        User writer = new User(1L, "writer", "password", "작성자", "writer@gmail");
        User other = new User(2L, "other", "password", "타인", "other@gmail");

        Question question = new Question("게시글", "게시글 내용").writeBy(writer);

        assertThatThrownBy(() -> question.deleteBy(other))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문은 작성자 본인만 삭제할 수 있습니다.");
    }

    @DisplayName("본인이 작성한 답변만 있다면 질문 삭제 가능")
    @Test
    void containsAnswerWrittenByWriter_deleteByWriter() throws CannotDeleteException {
        User writer = new User(1L, "writer", "password", "작성자", "writer@gmail");
        Question question = new Question("게시글", "게시글 내용").writeBy(writer);
        question.addAnswer(new Answer(writer, question, "작성자가 작성한 답변입니다."));

        assertThat(question.isDeleted()).isFalse();
        question.deleteBy(writer);

        assertThat(question.isDeleted()).isTrue();
    }

    @DisplayName("다른 사람이 쓴 답변이 존재하면 삭제 시 예외")
    @Test
    void containsAnswerWrittenByOther_throwsException() {
        User writer = new User(1L, "writer", "password", "작성자", "writer@gmail");
        User other = new User(2L, "other", "password", "타인", "other@gmail");

        Question question = new Question("게시글", "게시글 내용").writeBy(writer);
        question.addAnswer(new Answer(other, question, "다른 사람이 쓴 답변입니다."));

        assertThatThrownBy(() -> question.deleteBy(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
    
    @DisplayName("다른 사람이 쓴 답변이 이미 삭제되었다면 삭제 가능")
    @Test
    void containsAnswerWrittenByOther_alreadyDeleted() throws CannotDeleteException {
        User writer = new User(1L, "writer", "password", "작성자", "writer@gmail");
        User other = new User(2L, "other", "password", "타인", "other@gmail");

        Question question = new Question("게시글", "게시글 내용").writeBy(writer);
        Answer answer = new Answer(other, question, "다른 사람이 쓴 답변입니다.");
        question.addAnswer(answer);
        answer.deleteBy(other);

        assertThat(question.isDeleted()).isFalse();
        question.deleteBy(writer);

        assertThat(question.isDeleted()).isTrue();
    }
}
