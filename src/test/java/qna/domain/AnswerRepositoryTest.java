package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;

@RepositoryTest
class AnswerRepositoryTest {

    private UserRepository userRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    public AnswerRepositoryTest(
            UserRepository userRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository
    ) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Test
    void save() {
        // given
        User writer = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        Question savedQuestion = questionRepository.save(question);
        Answer answer = new Answer(savedWriter, savedQuestion, "투표했습니다");

        // when
        Answer savedAnswer = answerRepository.save(answer);

        // then
        assertAll(
                () -> assertThat(savedAnswer).isNotNull(),
                () -> assertThat(savedAnswer.getWriter()).isEqualTo(savedWriter),
                () -> assertThat(savedAnswer.getQuestion()).isEqualTo(savedQuestion)
        );
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        // given
        User writer = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        Question savedQuestion = questionRepository.save(question);
        Answer answer = new Answer(savedWriter, savedQuestion, "투표했습니다");

        User writer1 = new User("aiaiai1", "abc1234123", "루쿠", "aiaiaia1@gmail.com");
        User savedWriter1 = userRepository.save(writer1);
        Question question1 = new Question("머먹지?", "밥");
        Question savedQuestion1 = questionRepository.save(question1);
        Answer newAnswer = new Answer(savedWriter1, savedQuestion1, "투표했습니다");
        newAnswer.setDeleted(true);

        answerRepository.save(answer);
        answerRepository.save(newAnswer);

        // when
        List<Answer> findAnswers = answerRepository.findByQuestionIdAndDeletedFalse(answer.getQuestion().getId());

        // then
        assertAll(
                () -> assertThat(findAnswers).hasSize(1),
                () -> assertThat(findAnswers.get(0)).usingRecursiveComparison().isEqualTo(answer)
        );
    }

    @Test
    void findAll() {
        // given
        User writer = new User("tjdtls690", "abc1234@", "아벨", "tjdtls690@gmail.com");
        User savedWriter = userRepository.save(writer);
        Question question = new Question("투표해야하는가?", "투표하세요");
        Question savedQuestion = questionRepository.save(question);
        Answer answer = new Answer(savedWriter, savedQuestion, "투표했습니다");

        User writer1 = new User("aiaiai1", "abc1234123", "루쿠", "aiaiaia1@gmail.com");
        User savedWriter1 = userRepository.save(writer1);
        Question question1 = new Question("머먹지?", "밥");
        Question savedQuestion1 = questionRepository.save(question1);
        Answer newAnswer = new Answer(savedWriter1, savedQuestion1, "투표했습니다");

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
