package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    @DisplayName("질문자가 맞는지 확인한다.")
    @Test
    void isOwner_true() {
        // given
        User d2 = new User("D2", "1234", "박정훈", "test1@test.com");
        Question d2Question = new Question("title1", "contents1").writeBy(d2);

        // when
        boolean isOwner = d2Question.isOwner(d2);

        // then
        assertThat(isOwner).isTrue();
    }

    @DisplayName("질문자가 아닌지 확인한다.")
    @Test
    void isOwner_false() {
        // given
        User d2 = new User("D2", "1234", "박정훈", "test1@test.com");
        User ethan = new User("ethan", "1234", "김석호", "test@test.com");
        Question d2Question = new Question("title1", "contents1").writeBy(d2);

        // when
        boolean isOwner = d2Question.isOwner(ethan);

        // then
        assertThat(isOwner).isFalse();
    }

    @DisplayName("질문에 답변이 달린다.")
    @Test
    void addAnswer() {
        // given
        User questionWriter = new User("D2", "1234", "박정훈", "test1@test.com");
        Question question = new Question("title1", "contents1").writeBy(questionWriter);

        User answerWriter = new User("ethan", "1234", "김석호", "test@test.com");
        Answer answer = new Answer(answerWriter, question, "내용");

        question.addAnswer(answer);
        Question answerQuestion = answer.getQuestion();

        // when, then
        assertThat(question)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(answerQuestion);
    }
}
