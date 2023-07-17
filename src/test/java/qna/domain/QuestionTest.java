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
        User owner = new User("D2", "1234", "박정훈", "test1@test.com");
        Question ownerQuestion = new Question("title1", "contents1").writeBy(owner);

        // when
        boolean isOwner = ownerQuestion.isOwner(owner);

        // then
        assertThat(isOwner).isTrue();
    }

    @DisplayName("질문자가 아닌지 확인한다.")
    @Test
    void isOwner_false() {
        // given
        User owner = new User(1L, "D2", "1234", "박정훈", "test1@test.com");
        User notOwner = new User(2L, "ethan", "1234", "김석호", "test@test.com");
        Question ownerQuestion = new Question("title1", "contents1").writeBy(owner);

        // when
        boolean isOwner = ownerQuestion.isOwner(notOwner);

        // then
        assertThat(isOwner).isFalse();
    }

    @DisplayName("질문에 답변이 달린다.")
    @Test
    void addAnswer() {
        // given
        User questionWriter = new User("D2", "1234", "박정훈", "test1@test.com");
        Question expected = new Question("title1", "contents1").writeBy(questionWriter);

        User answerWriter = new User("ethan", "1234", "김석호", "test@test.com");
        Answer answer = new Answer(answerWriter, expected, "내용");

        expected.addAnswer(answer);
        Question actual = answer.getQuestion();

        // when, then
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }
}
