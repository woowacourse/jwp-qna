package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;
import qna.utils.fixture.UserFixture;

class QuestionTest {

    @Test
    @DisplayName("해당 질문의 작성자가 아니면 삭제할 수 없다")
    void deleteFail_NotOwner() {
        User user = UserFixture.JAVAJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);

        User otherUser = UserFixture.SANJIGI;

        assertThatThrownBy(() -> question.delete(otherUser))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문 작성자가 아닌 유저의 답변이 있으면 삭제할 수 없다.")
    void deleteFail_AnswerExists() {
        User user = UserFixture.JAVAJIGI;
        User otherUser = UserFixture.SANJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);

        question.addAnswer(new Answer(otherUser, question, "답변1"));

        assertThatThrownBy(() -> question.delete(user))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다")
    void deleteSuccess_SelfAnswer() {
        User user = UserFixture.JAVAJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);
        question.addAnswer(new Answer(user, question, "답변1"));

        question.delete(user);
        List<Answer> answers = question.getAnswers();

        assertThat(question.isDeleted()).isTrue();
        assertThat(answers.get(0).isDeleted()).isTrue();
    }
}
