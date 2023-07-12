package qna.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import qna.exception.CannotDeleteException;
import qna.fixture.Fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static qna.fixture.Fixture.Q1;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class QuestionTest {

    @Test
    void deleteBy_질문자인_경우() {
        // given
        final User writer = Q1.getWriter();

        // when
        Q1.deleteBy(writer);

        // then
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    void deleteBy_질문자가_아닌_경우() {
        // given
        final User writer = UserTest.SANJIGI;

        // expect
        assertThatThrownBy(() -> Q1.deleteBy(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageContaining("질문을 삭제할 권한이 없습니다.");
    }
}
