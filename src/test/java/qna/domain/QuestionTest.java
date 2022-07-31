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

        assertThatThrownBy(() -> question.deleteAndCreateDeleteHistories(otherUser))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문 작성자가 아닌 유저의 답변이 있으면 삭제할 수 없다.")
    void deleteFail_AnswerExists() {
        User user = UserFixture.JAVAJIGI;
        User otherUser = UserFixture.SANJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);

        question.addAnswer(new Answer(otherUser, question, "답변1"));

        assertThatThrownBy(() -> question.deleteAndCreateDeleteHistories(user))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다")
    void deleteSuccess_SelfAnswer() {
        User user = UserFixture.JAVAJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);
        question.addAnswer(new Answer(user, question, "답변1"));

        question.deleteAndCreateDeleteHistories(user);
        List<Answer> answers = question.getAnswers();

        assertThat(question.isDeleted()).isTrue();
        assertThat(answers.get(0).isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문을 중복해서 삭제하려고 하면 예외를 반환한다")
    void deleteFail_DuplicatedDelete() {
        User user = UserFixture.JAVAJIGI;
        Question question = new Question("title1", "contents1").writeBy(user);
        question.addAnswer(new Answer(user, question, "답변1"));
        question.deleteAndCreateDeleteHistories(user);

        assertThatThrownBy(() -> question.deleteAndCreateDeleteHistories(user))
                .isInstanceOf(CannotDeleteException.class);
    }
}
