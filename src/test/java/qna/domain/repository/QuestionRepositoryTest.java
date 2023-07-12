package qna.domain.repository;

import org.junit.jupiter.api.Test;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class QuestionRepositoryTest extends RepositoryTest {

    private final QuestionRepository questionRepository;

    QuestionRepositoryTest(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Test
    void 삭제되지_않은_질문들을_찾을_수_있다() {
        // given
        final User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        final Question question = new Question("question", "content").writeBy(user);
        questionRepository.save(question);

        // when
        final List<Question> actual = questionRepository.findByDeletedFalse();

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    void id로_질문을_찾을_수_있다() {
        // given
        final User user = new User("javajigi", "password", "name", "javajigi@slipp.net");
        final Question expected = questionRepository.save(new Question("question", "content").writeBy(user));

        // when
        final Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertThat(actual).contains(expected);
    }
}
