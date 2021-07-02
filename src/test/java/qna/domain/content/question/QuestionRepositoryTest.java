package qna.domain.content.question;

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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        questionWriter = TestUser.create();
        questionWriter = userRepository.save(questionWriter);
    }

    @DisplayName("삭제가 안된 모든 질문을 가지고 온다.")
    @Test
    void findByDeletedFalse() {
        Question question = TestQuestion.create(questionWriter, Collections.emptyList());
        question = questionRepository.save(question);
        flushAndClear();

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

        Question questionFromRepo = questionRepository
                .findByIdAndDeletedFalse(question.getId())
                .orElseThrow(AssertionError::new);
        assertThat(questionFromRepo).isEqualTo(question);

        questionFromRepo.deleteBy(questionWriter, LocalDateTime.now());
        flushAndClear();

        Optional<Question> nullQuestion = questionRepository
                .findByIdAndDeletedFalse(questionFromRepo.getId());

        assertThat(nullQuestion.isPresent()).isFalse();
    }

    @DisplayName("question에 answer를 add하더라도 정상적으로 영속되는지 확인한다.")
    @Test
    public void questionAddAnswer() {
        Question question = createQuestion();
        Answer answer = createAnswer();

        question.addAnswer(answer);
        flushAndClear();

        Question updatedQuestion = questionRepository
                .findByIdAndDeletedFalse(question.getId())
                .orElseThrow(AssertionError::new);

        assertThat(updatedQuestion.getAnswers()).isNotEmpty();
    }

    private Question createQuestion() {
        Question question = TestQuestion.create(questionWriter, Collections.emptyList());
        question = questionRepository.save(question);
        return question;
    }

    private Answer createAnswer() {
        User answerWriter = TestUser.create();
        return answerRepository.save(
                TestAnswer.create(userRepository.save(answerWriter))
        );
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}