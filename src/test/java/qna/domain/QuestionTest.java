package qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import qna.CannotDeleteException;

public class QuestionTest {

    private User user;

    private Question question;

    @BeforeEach
    void setUp() {
        user = new User(1L, "dusdn1702", "sally", "yeonwoo", "dusdn1702@naver.com");
        question = new Question(1L, "test", "test").writeBy(user);
    }

    @Test
    @DisplayName("질문의 주인을 확인한다.")
    void persist() {
        assertThat(question.isOwner(user)).isTrue();
    }

    @Test
    @DisplayName("질문에 답변을 추가한다.")
    void checkOwner() {
        Answer answer = new Answer(user, question, "content");
        question.addAnswer(answer);

        assertThat(question.getAnswers()).containsExactly(answer);
    }

    @Test
    @DisplayName("질문을 삭제하고 삭제되었는지 확인한다.")
    void checkDeleted() {
        question.setDeleted(true);

        assertThat(question.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문을 삭제하고 삭제한 이력을 반환한다.")
    void delete() throws CannotDeleteException {
        List<DeleteHistory> deleteHistoryList = question.delete(user);

        assertThat(deleteHistoryList).containsExactly(
            new DeleteHistory(
                ContentType.QUESTION,
                question.getId(),
                question.getWriter(),
                LocalDateTime.now()
            ));
    }

    @Test
    @DisplayName("다른 사용자가 질문을 삭제한다.")
    void deleteException() {
        User another = new User("asdf", "asdf", "asdf", "asdf@asdf.com");

        assertThatThrownBy(() -> question.delete(another))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    @DisplayName("질문에 답변을 추가하고 답변까지 삭제한다.")
    void deleteContainsAnswer() throws CannotDeleteException {
        Answer answer = new Answer(1L, user, question, "content");
        question.addAnswer(answer);

        List<DeleteHistory> deleteHistoryList = question.delete(user);

        assertThat(deleteHistoryList).containsExactly(
            new DeleteHistory(
                ContentType.QUESTION,
                question.getId(),
                question.getWriter(),
                LocalDateTime.now()
            ),
            new DeleteHistory(
                ContentType.ANSWER,
                answer.getId(),
                answer.getWriter(),
                LocalDateTime.now()
            ));
        assertThat(question.isDeleted()).isTrue();
        assertThat(question.getAnswers().stream()
            .allMatch(Answer::isDeleted)).isTrue();
    }

    @Test
    @DisplayName("질문에 작성자가 다른 답변이 존재해 삭제할 수 없다.")
    void deleteContainsAnswerException() {
        User another = new User("asdf", "asdf", "asdf", "asdf@asdf.com");
        Answer answer1 = new Answer(1L, user, question, "content");
        question.addAnswer(answer1);

        Answer answer2 = new Answer(2L, another, question, "content");
        question.addAnswer(answer2);

        assertThatThrownBy(() -> question.delete(user))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
