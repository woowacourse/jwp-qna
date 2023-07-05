package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        final Question question = new Question("test", "testtest");
        final Question savedQuestion = questionRepository.save(question);
        final User user = new User("test", "test1234", "test", "test@test.com");
        final User savedUser = userRepository.save(user);
        final Answer answer = new Answer(savedUser, savedQuestion, "test");
        answerRepository.save(answer);

        // when
        final List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(savedQuestion.getId());

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(answer);
    }

    @Test
    void findByIdAndDeletedFalse() {
        // given
        final Question question = new Question("test", "testtest");
        final Question savedQuestion = questionRepository.save(question);
        final User user = new User("test", "test1234", "test", "test@test.com");
        final User savedUser = userRepository.save(user);
        final Answer answer = new Answer(savedUser, savedQuestion, "test");
        answerRepository.save(answer);

        // when
        final Answer actual = answerRepository.findByIdAndDeletedFalse(answer.getId()).get();

        // then
        assertThat(actual).isEqualTo(answer);
    }
}
