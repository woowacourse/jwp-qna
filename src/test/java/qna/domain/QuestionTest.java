package qna.domain;

import static qna.fixtures.QuestionFixture.Q1;
import static qna.fixtures.UserFixture.JAVAJIGI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {

    @DisplayName("어떤 사용자의 질문인지 확인할 수 있다.")
    @Test
    void isOwner() {
        // given
        final User user = JAVAJIGI;

        // when, then
        Assertions.assertThat(Q1.isOwner(user)).isTrue();
    }
}
