package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EntityManager em;


    @Test
    void 질문을_저장한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));

        // when
        Question actual = questionRepository.save(new Question("title", "content", user));

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 질문을_조회한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        Question expected = questionRepository.save(new Question("title", "content", user));

        // when
        Question actual = questionRepository.findById(expected.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void 질문을_조회할_떄_삭제된_답변은_제외하고_조회한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        Question expected = questionRepository.save(new Question("title", "content", user));
        Answer deletedAnswer = new Answer(user, expected, "any");
        deletedAnswer.delete();

        Answer answer = new Answer(user, expected, "any");
        answerRepository.saveAll(Arrays.asList(deletedAnswer, answer));

        em.clear();

        // when
        Question actual = questionRepository.findByIdAndDeletedFalse(expected.getId()).get();

        // then
        assertAll(
                () -> assertThat(actual.getAnswers().size()).isEqualTo(1),
                () -> assertThat(actual.getAnswers()).noneMatch(Answer::isDeleted)
        );
    }
}
