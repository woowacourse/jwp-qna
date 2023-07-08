package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class AnswerRepositoryTest extends RepositoryTest {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private Answer answer;

    AnswerRepositoryTest(final AnswerRepository answerRepository, final UserRepository userRepository, final QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @BeforeEach
    void setUp() {
        final User user = userRepository.save(UserTest.JAVAJIGI);
        final Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user));
        answer = new Answer(user, question, "JPA 신기하고 재밌어요");
    }

    @Test
    void 질문_id로_답변들을_찾을_수_있다() {
        // given
        final Answer expected = answerRepository.save(answer);

        // when
        final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(expected.getQuestion().getId());

        // then
        assertThat(actual).contains(expected);
    }

    @Test
    void id로_답변을_찾을_수_있다() {
        // given
        final Answer expected = answerRepository.save(answer);

        // when
        final Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertThat(actual).contains(expected);
    }
}
