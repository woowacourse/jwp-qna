package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private User savedWriter;
    private Question savedQuestion;
    private Answer answer;

    @BeforeEach
    void setUp() {
        User writer = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        savedQuestion = questionRepository.save(question);
        answer = new Answer(savedWriter, savedQuestion, "투표했습니다");
    }

    @Test
    void save() {
        // when
        Answer savedAnswer = answerRepository.save(answer);

        // then
        assertAll(
                () -> assertThat(savedAnswer).isNotNull(),
                () -> assertThat(savedAnswer.getWriterId()).isEqualTo(savedWriter.getId()),
                () -> assertThat(savedAnswer.getQuestionId()).isEqualTo(savedQuestion.getId())
        );
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        User writer = new User("tjdtls6901", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        Question savedQuestion = questionRepository.save(question);
        Answer newAnswer = new Answer(savedWriter, savedQuestion, "투표했습니다");
        newAnswer.setDeleted(true);

        answerRepository.save(answer);
        answerRepository.save(newAnswer);

        // when
        List<Answer> findAnswers = answerRepository.findByQuestionIdAndDeletedFalse(answer.getQuestionId());

        // then
        assertAll(
                () -> assertThat(findAnswers).hasSize(1),
                () -> assertThat(findAnswers.get(0)).usingRecursiveComparison().isEqualTo(answer)
        );
    }

    @Test
    void findAll() {
        // given
        User writer = new User("tjdtls6901", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        Question savedQuestion = questionRepository.save(question);
        Answer newAnswer = new Answer(savedWriter, savedQuestion, "투표했습니다");

        answerRepository.save(answer);
        answerRepository.save(newAnswer);

        // when
        List<Answer> answers = answerRepository.findAll();

        // then
        assertAll(
                () -> assertThat(answers).hasSize(2),
                () -> assertThat(answers.get(0)).usingRecursiveComparison().isEqualTo(answer),
                () -> assertThat(answers.get(1)).usingRecursiveComparison().isEqualTo(newAnswer)
        );
    }

}