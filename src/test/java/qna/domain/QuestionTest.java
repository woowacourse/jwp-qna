package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixtures.UserFixture.JAVAJIGI;
import static qna.fixtures.UserFixture.SANJIGI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class QuestionTest {

    public static final Question Q1 = new Question("title1", "contents1").writeBy(JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(SANJIGI);

    @DisplayName("어떤 사용자의 질문인지 확인할 수 있다.")
    @Test
    void isOwner() {
        // given
        final User user = JAVAJIGI;

        // when, then
        assertThat(Q1.isOwner(user)).isTrue();
    }
}
