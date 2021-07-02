package qna.domain.content.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.domain.user.TestUser;
import qna.domain.content.answer.Answer;
import qna.domain.content.answer.TestAnswer;
import qna.domain.log.DeleteHistory;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private User answerWriter;
    private User questionWriter;
    private Answer answer;
    private List<Answer> answers;
    private Question question;

    @BeforeEach
    void setUp() {
        answerWriter = TestUser.createWithId();
        questionWriter = TestUser.createWithId();
        answer = TestAnswer.createWithId(answerWriter);
        answers = Collections.singletonList(answer);
        question = TestQuestion.createWithId(questionWriter, answers);
    }

    @DisplayName("새로운 답변을 추가한다.")
    @Test
    void addAnswer() {
        Answer newAnswer = TestAnswer.create(answerWriter);

        question.addAnswer(newAnswer);

        assertThat(question.getAnswers()).containsExactly(
                answer,
                newAnswer
        );
    }

    @DisplayName("질문을 삭제한다.")
    @Test
    void deleteBy_deleteQuestion() {
        final LocalDateTime NOW = LocalDateTime.of(2021, 7, 1, 0, 0, 0);

        question = TestQuestion.create(questionWriter, Collections.emptyList());
        List<DeleteHistory> deleteHistories =
                question.deleteBy(questionWriter, NOW);

        assertAll(
                () -> assertThat(deleteHistories)
                        .usingRecursiveComparison()
                        .isEqualTo(Collections.singletonList(
                                        new DeleteHistory(question, questionWriter, NOW)
                                )),
                () -> assertThat(question.isDeleted()).isTrue()
        );
    }

    @DisplayName("질문 삭제시 자신의 질문이 이니면 예외.")
    @Test
    void deleteBy_causeExceptionWhenQuestionIsNotMine() {
        final LocalDateTime NOW = LocalDateTime.of(2021, 7, 1, 0, 0, 0);

        assertThatThrownBy(
                () -> question.deleteBy(answerWriter, NOW)
        ).isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @DisplayName("질문 삭제시 다른사람의 답변이 달려있으면 예외")
    @Test
    void deleteBy_causeExceptionWhenQuestionHasAnswerOfOtherUser() {
        final LocalDateTime NOW = LocalDateTime.of(2021, 7, 1, 0, 0, 0);

        assertThatThrownBy(
                () -> question.deleteBy(questionWriter, NOW)
        ).isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    void getId() {
        assertThat(question.getId()).isNotNull();
    }

    @Test
    void getTitle() {
        assertThat(question.getTitle()).isEqualTo(TestQuestion.TITLE);
    }

    @Test
    void getContents() {
        assertThat(question.getContents()).isEqualTo(TestQuestion.CONTENTS);
    }

    @Test
    void getWriterId() {
        assertThat(question.getWriterId()).isEqualTo(questionWriter.getId());
    }

    @Test
    void getAnswers() {
        assertThat(question.getAnswers()).containsExactly(answer);
    }
}