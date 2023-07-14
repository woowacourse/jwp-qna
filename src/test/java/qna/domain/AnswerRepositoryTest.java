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

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private User javajigi;
    private User sanjigi;
    private Question question1;
    private Question question2;

    public AnswerRepositoryTest(final UserRepository userRepository, final AnswerRepository answerRepository,
                                final QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @BeforeEach
    void setUp() {
        this.javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        this.sanjigi = new User("sanjigi", "password", "name", "sanjigi@slipp.net");
        userRepository.save(javajigi);
        userRepository.save(sanjigi);
        this.question1 = questionRepository.save(new Question("title1", "contents1").writeBy(javajigi));
        this.question2 = questionRepository.save(new Question("title2", "contents2").writeBy(sanjigi));
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        final Answer answer1 = new Answer(javajigi, question1, "Answers Contents1");
        final Answer answer2 = new Answer(sanjigi, question1, "Answers Contents2");

        answerRepository.save(answer1);
        answerRepository.save(answer2);

        final List<Answer> 찾은거 = answerRepository.findByQuestionIdAndDeletedFalse(answer1.getQuestion().getId());
        assertThat(찾은거).hasSize(2);
    }

    @Test
    void findByIdAndDeletedFalse() {
        final Answer answer = new Answer(javajigi, question1, "Answers Contents1");

        answerRepository.save(answer);
        assertThat(
                answerRepository.findByIdAndDeletedFalse(answer.getId())
        ).isPresent();

        answer.setDeleted(true);

        assertThat(
                answerRepository.findByIdAndDeletedFalse(answer.getId())
        ).isEmpty();
    }
}
