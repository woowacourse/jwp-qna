package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @DisplayName("Question을 DB에 저장한다.")
    @Test
    void save() {
        Question expected = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
        Question actual = questions.save(expected);

        assertAll(() -> {
            assertThat(expected).isSameAs(actual);
            assertThat(expected.getId()).isEqualTo(1L);

            assertThat(actual.getCreatedAt()).isNotNull();
            assertThat(actual.isDeleted()).isFalse();
            assertThat(actual.getTitle()).isNotNull();
        });
    }
}
