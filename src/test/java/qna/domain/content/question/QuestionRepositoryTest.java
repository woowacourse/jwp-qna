package qna.domain.content.question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import qna.domain.content.answer.Answer;
import qna.domain.content.answer.AnswerRepository;
import qna.domain.content.answer.TestAnswer;
import qna.domain.user.TestUser;
import qna.domain.user.User;
import qna.domain.user.UserRepository;
import qna.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QuestionRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    private User questionWriter;

    @BeforeEach
    void setUp() {
        questionWriter = userRepository.save(TestUser.create());
    }

    @DisplayName("삭제가 안된 모든 질문을 가지고 온다.")
    @Test
    void findByDeletedFalse() {
        Question question = createQuestion();

        List<Question> questions = questionRepository.findByDeletedFalse();
        assertThat(questions).containsExactly(question);

        question.deleteBy(questionWriter, LocalDateTime.now());
        questions = questionRepository.findByDeletedFalse();

        assertThat(questions).isEmpty();
    }

    @DisplayName("삭제가 안된 질문을 id기반으로 가지고 온다.")
    @Test
    void findByIdAndDeletedFalse() {
        Question question = createQuestion();
        flushAndClear();

        Question questionFromRepo = findQuestionById(question.getId());
        assertThat(questionFromRepo).isEqualTo(question);

        questionFromRepo.deleteBy(questionWriter, LocalDateTime.now());
        flushAndClear();

        Assertions.assertThatThrownBy(
                () -> findQuestionById(questionFromRepo.getId())
        ).isInstanceOf(NotFoundException.class);
    }

    @DisplayName("답변을 삭제한다.")
    @Test
    void removeAnswer() {
        Answer answer = createAnswer();
        Question question = createQuestion();
        question.addAnswer(answer);
        flushAndClear();

        question = findQuestionById(question.getId());
        answer = findAnswerById(answer.getId());
        question.removeAnswer(answer);
        flushAndClear();

        question = findQuestionById(question.getId());
        assertThat(question.getAnswers()).isEmpty();
    }

    @DisplayName("question에 answer를 add하더라도 정상적으로 영속되는지 확인한다.")
    @Test
    public void questionAddAnswer() {
        Question question = createQuestion();
        Answer answer = createAnswer();

        question.addAnswer(answer);
        flushAndClear();

        Question updatedQuestion = findQuestionById(question.getId());

        assertThat(updatedQuestion.getAnswers()).isNotEmpty();
    }

    private Answer findAnswerById(Long id) {
        return answerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    private Question findQuestionById(Long id) {
        return questionRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    private Question createQuestion() {
        return questionRepository.save(
                TestQuestion.create(questionWriter)
        );
    }

    private Answer createAnswer() {
        return answerRepository.save(
                TestAnswer.create(
                        userRepository.save(questionWriter)
                )
        );
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}