package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("생성한다.")
    @Nested
    class Create {
        @DisplayName("답변을 생성한다.")
        @Test
        void success() {
            // given
            User answerWriter = new User("ethan", "1234", "김석호", "test@test.com");
            User questionWriter = new User("D2", "1234", "박정훈", "test1@test.com");
            Question question = new Question("title1", "contents1").writeBy(questionWriter);

            // when, then
            assertDoesNotThrow(() -> new Answer(answerWriter, question, "내용"));
        }

        @DisplayName("답변자가 존재하지 않으면 실패한다.")
        @Test
        void fail_unAuthorized() {
            // given
            User questionWriter = new User("D2", "1234", "박정훈", "test1@test.com");
            Question question = new Question("title1", "contents1").writeBy(questionWriter);

            // when, then
            assertThatThrownBy(() -> new Answer(null, question, "내용"))
                    .isInstanceOf(UnAuthorizedException.class);
        }

        @DisplayName("질문이 존재하지 않으면 실패한다.")
        @Test
        void fail_question_notFound() {
            // given
            User answerWriter = new User("ethan", "1234", "김석호", "test@test.com");

            // when, then
            assertThatThrownBy(() -> new Answer(answerWriter, null, "내용"))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}
