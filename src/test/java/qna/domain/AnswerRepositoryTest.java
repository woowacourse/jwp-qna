package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    private User writer;
    private Question question;

    public AnswerRepositoryTest(AnswerRepository answerRepository, UserRepository userRepository,
                                QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @BeforeEach
    void setUp() {
        writer = new User("gray", "password", "pium", "pium@google.com");
        userRepository.save(writer);

        question = new Question("1번 질문", "1번 질문에 대한 내용입니다.");
        questionRepository.save(question);
    }

    @Test
    void save() {
        Answer answer = new Answer(writer, question, "질문에 대한 답변입니다.");

        Answer savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer answer = new Answer(writer, question, "1번 질문에 대한 답변입니다.");
        Answer savedAnswer = answerRepository.save(answer);

        assertThat(answerRepository.findByIdAndDeletedFalse(savedAnswer.getId())).isPresent();
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        Question question2 = questionRepository.save(new Question("2번 질문입니다.", "2번 질문 내용입니다."));
        Answer answer1 = new Answer(writer, question, "1번 질문에 대한 답변입니다.");
        Answer answer2 = new Answer(writer, question2, "2번 질문에 대한 답변입니다.");
        answerRepository.save(answer1);
        answerRepository.save(answer2);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertThat(answers).contains(answer1);
    }
}
