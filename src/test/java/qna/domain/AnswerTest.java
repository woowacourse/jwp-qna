package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.fixture.UserFixture.JAVAJIGI;
import static qna.fixture.UserFixture.SANJIGI;

@DataJpaTest
public class AnswerTest {

    private Answer A1;
    private Answer A2;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = userRepository.save(JAVAJIGI);
        User user2 = userRepository.save(SANJIGI);

        Question question = questionRepository.save(new Question("title1", "contents1").writeBy(user1));

        A1 = new Answer(user1, question, "Answers Contents1");
        A2 = new Answer(user2, question, "Answers Contents2");
    }

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

    @Test
    void Question을_조회한다() {
        // given, when
        Answer savedAnswer = answerRepository.save(A1);

        Question question = savedAnswer.getQuestion();

        // then
        assertThat(question.getTitle()).isEqualTo("title1");
    }
}
