package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class QuestionRepositoryTest {

    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserRepositoryTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserRepositoryTest.SANJIGI);

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void save_메서드로_Q1을_저장한다() {
        // when
        final Question actual = questionRepository.save(Q1);

        // then
        assertThat(actual.getId()).isPositive();
    }

    @Test
    void findById_메서드로_Q1을_조회한다() {
        // given
        final Question q1 = questionRepository.save(Q1);

        // when
        final Optional<Question> actual = questionRepository.findById(q1.getId());

        // then
        assertThat(actual).isPresent();
    }
}
