package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void 질문을_저장한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));

        // when
        Question actual = questionRepository.save(new Question("title", "content", user, false));

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 질문을_조회한다() {
        // given
        User user = userRepository.save(new User("userId", "password", "name", "email@naver.com"));
        Question actual = questionRepository.save(new Question("title", "content", user, false));

        // when
        Question expected = questionRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
