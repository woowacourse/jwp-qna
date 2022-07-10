package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.domain.support.QuestionFixture.createQuestion;
import static qna.domain.support.UserFixture.huni;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("삭제되지 않은 질문들을 조회한다.")
    @Test
    void findByDeletedFalse() {
        User user = huni();
        userRepository.save(user);

        questionRepository.save(createQuestion(user.getId()));
        questionRepository.save(createQuestion("t1", "c1", user.getId()));

        assertThat(questionRepository.findByDeletedFalse()).hasSize(2);
    }

    @DisplayName("삭제되지 않은 질문 하나를 조회한다.")
    @Test
    void findByIdDeletedFalse() {
        User user = huni();
        userRepository.save(user);

        Question question = questionRepository.save(createQuestion(user.getId()));
        Question foundQuestion = questionRepository.findByIdAndDeletedFalse(question.getId()).get();

        assertAll(
                () -> assertThat(foundQuestion).isNotNull(),
                () -> assertThat(foundQuestion).isEqualTo(question)
        );
    }
}
