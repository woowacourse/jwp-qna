package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void saveAnswer() {
        // given
        User user = new User(1L, "칙촉", "비밀번호", "안영윤", "qnfrrmfo@naver.com");
        Question question = new Question(1L, "제목", "내용");
        Answer answer = new Answer(user, question, "답변내용");

        // when
        Answer actual = answerRepository.save(answer);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(answer).isSameAs(actual);
    }
}
