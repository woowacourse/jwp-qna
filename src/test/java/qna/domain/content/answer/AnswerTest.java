package qna.domain.content.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.domain.user.TestUser;
import qna.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerTest {
    private User questionWriter;
    private User answerWriter;
    private Answer answer;

    @BeforeEach
    void setUp() {
        questionWriter = TestUser.createWithId();
        answerWriter = TestUser.createWithId();
        answer = TestAnswer.createWithId(answerWriter);
    }

    @DisplayName("답변 작성자가 맞으면 true")
    @Test
    void isOwner_inTureCase() {
        assertThat(answer.isOwner(answerWriter)).isTrue();
    }

    @DisplayName("답변 작성자가 틀리면 false")
    @Test
    void isOwner_inFalseCase() {
        assertThat(answer.isOwner(questionWriter)).isFalse();
    }

    @DisplayName("답변이 삭제되어 있지 않으면 false")
    @Test
    void isDeleted_inFalseCase() {
        assertThat(answer.isDeleted()).isFalse();
    }

    @DisplayName("답변이 삭제되어 있으면 true")
    @Test
    void isDeleted_inTrueCase() {
        answer.toDeleted();
        assertThat(answer.isDeleted()).isTrue();
    }

    @DisplayName("답변이 삭제되었다고 표시한다.")
    @Test
    void toDeleted() {
        answer.toDeleted();
        assertThat(answer.isDeleted()).isTrue();
    }

    @DisplayName("primary key(id)를 반환한다.")
    @Test
    void getId() {
        assertThat(answer.getId()).isNotNull();
    }

    @DisplayName("content를 반환한다.")
    @Test
    void getContents() {
        assertThat(answer.getContents()).isEqualTo(TestAnswer.CONTENTS);
    }
}