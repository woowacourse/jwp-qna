package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    private User user;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        this.user = new User(1L, "칙촉", "비밀번호", "안영윤", "qnfrrmfo@naver.com");
        this.question = new Question(1L, "제목", "내용");
        this.answer = new Answer(user, question, "답변내용");
    }

    @Test
    void saveAnswer() {
        // given, when
        Answer actual = answerRepository.save(answer);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(answer).isSameAs(actual);
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        answerRepository.save(answer);

        // when
        Answer actual = answerRepository.findByQuestionIdAndDeletedFalse(question.getId()).get(0);

        // then
        assertThat(actual).isSameAs(answer);
    }

    @Test
    void findByIdAndDeletedFalse() {
        // given
        answerRepository.save(answer);

        // when
        Answer actual = answerRepository.findByIdAndDeletedFalse(question.getId()).get();

        // then
        assertThat(actual).isSameAs(answer);
    }
}
