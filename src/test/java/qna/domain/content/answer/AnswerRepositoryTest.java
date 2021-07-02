package qna.domain.content.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import qna.domain.user.TestUser;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
class AnswerRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

    private User writer;
    private Answer answer;

    @BeforeEach
    void setUp() {
        writer = TestUser.create();
        userRepository.save(writer);

        answer = TestAnswer.create(writer);
        answerRepository.save(answer);
    }

    @DisplayName("삭제되지 않은 질문을 아이디 기반으로 찾는다.")
    @Test
    void findByQuestionIdAndDeletedFalse_inTrueCase() {
        Answer answer = answerRepository.findByIdAndDeletedFalse(this.answer.getId())
                .orElseThrow(AssertionError::new);

        assertThat(answer.getId()).isEqualTo(this.answer.getId());
    }

    @DisplayName("삭제된 질문은 검색되지 않는다.")
    @Test
    void findByQuestionIdAndDeletedFalse_inFalseCase() {
        Answer answer = answerRepository.findById(this.answer.getId())
                .orElseThrow(AssertionError::new);

        answer.toDeleted();
        flushAndClear();

        boolean present = answerRepository.findByIdAndDeletedFalse(this.answer.getId())
                .isPresent();
        assertThat(present).isFalse();
    }

    private void flushAndClear() {
        testEntityManager.flush();
        testEntityManager.clear();
    }
}