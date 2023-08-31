package qna.domain.repository;

import org.junit.jupiter.api.Test;
import qna.domain.Answer;
import qna.domain.AnswerRepository;
import qna.domain.QuestionRepository;
import qna.domain.UserRepository;
import qna.domain.Question;
import qna.domain.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class AnswerRepositoryTest extends RepositoryTest {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    AnswerRepositoryTest(
            final AnswerRepository answerRepository,
            final UserRepository userRepository,
            final QuestionRepository questionRepository
    ) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Test
    void 질문_id로_답변들을_찾을_수_있다() {
        // given
        final User user = userRepository.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        final Question question = questionRepository.save(new Question("title", user, "content"));
        final Answer answer = new Answer(user, question, "cascade를 통해 상태 변화를 타 Entity에 전이시킬 수 있어요");
        final Answer expected = answerRepository.save(answer);

        // when
        final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(expected.getQuestion().getId());

        // then
        assertThat(actual).contains(expected);
    }

    @Test
    void id로_답변을_찾을_수_있다() {
        // given
        final User user = userRepository.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
        final Question question = questionRepository.save(new Question("title", user, "content"));
        final Answer answer = new Answer(user, question, "cascade를 통해 상태 변화를 타 Entity에 전이시킬 수 있어요");
        final Answer expected = answerRepository.save(answer);

        // when
        final Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertThat(actual).contains(expected);
    }

}
