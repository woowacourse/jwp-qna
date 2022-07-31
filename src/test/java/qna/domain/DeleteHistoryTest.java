package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

class DeleteHistoryTest {

    @DisplayName("삭제 된 질문과 답변에서 삭제 기록 생성")
    @Test
    void of_deletedQuestionAndAnswers() throws CannotDeleteException {
        User user = new User(1L, "user", "password", "사용자", "user@gmail.com");
        User other = new User(2L, "other", "password", "다른 사용자", "other@gmail.com");

        Question question = new Question(1L, "질문", "질문의 내용").writeBy(user);
        Answer answer = new Answer(1L, other, question, "답변 내용");
        question.addAnswer(answer);

        answer.deleteBy(other);
        question.deleteBy(user);

        DeleteHistory questionDeleteHistory = new DeleteHistory(ContentType.QUESTION, question.getId(), user,
                LocalDateTime.now());
        DeleteHistory answerDeleteHistory = new DeleteHistory(ContentType.ANSWER, answer.getId(), other,
                LocalDateTime.now());

        assertThat(DeleteHistory.of(question)).containsExactly(questionDeleteHistory, answerDeleteHistory);
    }

    @DisplayName("삭제되지 않은 질문은 삭제 기록 생성하지 않음")
    @Test
    void of_notDeletedQuestion() throws CannotDeleteException {
        User user = new User(1L, "user", "password", "사용자", "user@gmail.com");
        User other = new User(2L, "other", "password", "다른 사용자", "other@gmail.com");

        Question question = new Question(1L, "질문", "질문의 내용").writeBy(user);
        Answer answer = new Answer(1L, other, question, "답변 내용");
        question.addAnswer(answer);

        assertThat(DeleteHistory.of(question)).isEmpty();
    }
}