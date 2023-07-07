package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {

    @Autowired
    private AnswerRepository answerRepository;

    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void 답변을_저장한다() {
        Answer savedAnswer = answerRepository.save(A1);

        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    void 답변을_찾아온다() {
        // given, when
        Answer savedAnswer1 = answerRepository.save(A1);
        Answer savedAnswer2 = answerRepository.save(A2);

        List<Answer> answers = answerRepository.findAll();
        // then
        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers).containsExactlyInAnyOrder(savedAnswer1, savedAnswer2);
    }

    @Test
    void id로_답변을_조회한다() {
        // given, when
        Answer savedAnswer = answerRepository.save(A1);
        Answer findAnswer = answerRepository.findById(savedAnswer.getId()).get();

        // then
        assertThat(findAnswer).isEqualTo(savedAnswer);
    }
}
