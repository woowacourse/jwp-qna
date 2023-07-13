package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 답변을_저장한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        Question question = questionRepository.save(new Question("title", "content"));

        // when
        Answer actual = answerRepository.save(
                new Answer(user, question, "content"));

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 답변을_조회한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        Question question = questionRepository.save(new Question("title", "content"));
        Answer actual = answerRepository.save(new Answer(user, question, "content"));

        // when
        Answer expected = answerRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
