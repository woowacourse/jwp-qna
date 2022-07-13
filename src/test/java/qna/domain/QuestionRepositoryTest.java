package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
class QuestionRepositoryTest {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    public QuestionRepositoryTest(QuestionRepository questionRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @DisplayName("삭제되지 않은 질문을 조회한다.")
    @Test
    void findByDeletedFalse() {
        userRepository.save(JAVAJIGI);
        userRepository.save(SANJIGI);
        questionRepository.save(Q1);
        questionRepository.save(Q2);

        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 질문을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        userRepository.save(JAVAJIGI);
        Question question = questionRepository.save(Q1);

        Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(question.getId());
        assertThat(actual).hasValue(question);
    }
}
