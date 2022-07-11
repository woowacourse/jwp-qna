package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("질문id로 삭제되지 않은 응답들을 가져온다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        final User user = new User("seungpang", "12345678aA!", "김승래", "email@email.com");
        userRepository.save(user);

        final Question question = new Question("제목", "내용")
                .writeBy(user);
        questionRepository.save(question);

        answerRepository.save(new Answer(user, question, "내용"));
        final List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(
                question.getId());

        assertThat(answers).hasSize(1);
    }

    @DisplayName("응답id로 삭제되지 않은 응답 단건을 가져온다.")
    @Test
    void findByIdAndDeletedFalse() {
        final User user = new User("seungpang", "12345678aA!", "김승래", "email@email.com");
        userRepository.save(user);

        final Question question = new Question("제목", "내용")
                .writeBy(user);
        questionRepository.save(question);

        final Answer answer = answerRepository.save(new Answer(user, question, "내용"));
        final Answer findAnswer = answerRepository.findByIdAndDeletedFalse(answer.getId())
                .orElseThrow();

        assertAll(() -> {
            assertThat(findAnswer).isNotNull();
            assertThat(findAnswer).isEqualTo(answer);
        });
    }
}
