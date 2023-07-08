package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class QuestionRepositoryTest extends RepositoryTest {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private Question question1;
    private Question question2;

    QuestionRepositoryTest(final QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        final User user = userRepository.save(UserTest.JAVAJIGI);
        question1 = new Question("title1", "contents1").writeBy(user);
        question2 = new Question("title2", "contents2").writeBy(user);
    }

    @Test
    void 삭제되지_않은_질문들을_찾을_수_있다() {
        // given
        questionRepository.save(question1);
        questionRepository.save(question2);

        // when
        final List<Question> actual = questionRepository.findByDeletedFalse();

        // then
        assertThat(actual).hasSize(2);
    }

    @Test
    void id로_질문을_찾을_수_있다() {
        // given
        final Question expected = questionRepository.save(question1);

        // when
        final Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        Assertions.assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }
}
