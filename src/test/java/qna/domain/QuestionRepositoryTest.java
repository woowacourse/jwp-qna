package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("삭제되지 않는 질문들을 찾는다.")
    @Test
    void findByDeletedFalse() {
        final User user = new User("seungpang", "12345678aA!", "김승래", "email@email.com");
        userRepository.save(user);

        final Question question1 = new Question("제목1", "내용1")
                .writeBy(user);
        final Question question2 = new Question("제목2", "내용2")
                .writeBy(user);
        questionRepository.save(question1);
        questionRepository.save(question2);

        assertThat(questionRepository.findByDeletedFalse()).hasSize(2);
    }

    @DisplayName("삭제되지 않는 질문 단건을 찾는다.")
    @Test
    void findByIdAndDeletedFalse() {
        final User user = new User("seungpang", "12345678aA!", "김승래", "email@email.com");
        userRepository.save(user);

        final Question question = new Question("제목1", "내용1")
                .writeBy(user);
        final Question savedQuestion = questionRepository.save(question);
        final Question findQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId())
                .orElseThrow();

        assertAll(() -> {
            assertThat(findQuestion).isNotNull();
            assertThat(findQuestion).isEqualTo(savedQuestion);
        });
    }
}
