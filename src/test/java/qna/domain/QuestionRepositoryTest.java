package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;
import static qna.domain.UserTest.JAVAJIGI;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class QuestionRepositoryTest {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    @DisplayName("삭제되지 않은 질문을 조회한다.")
    @Test
    void findByDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Q1.writeBy(user);
        questionRepository.save(Q1);
        Q2.writeBy(user);
        questionRepository.save(Q2);

        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).hasSize(2);
    }

    @DisplayName("Id와 일치하고 삭제가 안된 질문을 조회한다.")
    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(JAVAJIGI);
        Q1.writeBy(user);
        Question question = questionRepository.save(Q1);

        Optional<Question> actual = questionRepository.findByIdAndDeletedFalse(question.getId());
        assertThat(actual).hasValue(question);
    }
}
