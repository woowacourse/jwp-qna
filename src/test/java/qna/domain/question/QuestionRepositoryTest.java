package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.user.UserTest;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @Test
    void findByDeletedFalse_메서드는_deleted_값이_거짓인_데이터의_리스트_반환() {
        final Question 문제1 = new Question("title1", "contents1", UserTest.JAVAJIGI);
        final Question 문제2 = new Question("title2", "contents2", UserTest.SANJIGI);
        문제2.setDeleted(true);
        questions.saveAll(List.of(문제1, 문제2));

        List<Question> actual = questions.findByDeletedFalse();
        List<Question> expected = List.of(문제1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdAndDeletedFalse_메서드는_id_값이_인자값과_동일하며_deleted_값이_거짓인_데이터를_Optional로_반환() {
        final Question 문제 = new Question("title1", "contents1", UserTest.JAVAJIGI);
        questions.save(문제);

        Optional<Question> actual1 = questions.findByIdAndDeletedFalse(문제.getId());
        Optional<Question> expected1 = Optional.of(문제);
        assertThat(actual1).isEqualTo(expected1);

        Optional<Question> actual2 = questions.findByIdAndDeletedFalse(9999L);
        Optional<Question> expected2 = Optional.ofNullable(null);
        assertThat(actual2).isEqualTo(expected2);
    }
}
