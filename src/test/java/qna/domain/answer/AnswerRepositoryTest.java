package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.question.QuestionTest;
import qna.domain.user.UserTest;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Test
    void findByQuestionIdAndDeletedFalse_메서드는_questionId_값이_인자값과_동일하며_deleted_값이_거짓인_데이터의_리스트_반환() {
        final Answer 자바지기의_답글 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        final Answer 산지기의_답글 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
        final Answer 산지기의_답글2 = new Answer(UserTest.SANJIGI, QuestionTest.Q2, "Answers Contents3");
        산지기의_답글.setDeleted(true);
        answers.saveAll(List.of(자바지기의_답글, 산지기의_답글, 산지기의_답글2));

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(QuestionTest.Q1.getId());
        List<Answer> expected = List.of(자바지기의_답글);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdAndDeletedFalse_메서드는_id_값이_인자값과_동일하며_deleted_값이_거짓인_데이터를_Optional로_반환() {
        final Answer 자바지기의_답글 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        answers.save(자바지기의_답글);

        Optional<Answer> actual1 = answers.findByIdAndDeletedFalse(자바지기의_답글.getId());
        Optional<Answer> expected1 = Optional.of(자바지기의_답글);
        assertThat(actual1).isEqualTo(expected1);

        Optional<Answer> actual2 = answers.findByIdAndDeletedFalse(9999L);
        Optional<Answer> expected2 = Optional.ofNullable(null);
        assertThat(actual2).isEqualTo(expected2);
    }
}
