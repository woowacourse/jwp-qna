package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = userRepository.save(new User("aaa", "1234", "jurl", "dbswnfl2"));
        Question question = questionRepository.save(new Question("title", "content"));
        Answer answer = new Answer(user, question, "content");
        Answer save = answerRepository.save(answer);

        List<Answer> byQuestionIdAndDeletedFalse = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertAll(
                () -> assertThat(byQuestionIdAndDeletedFalse.size()).isEqualTo(1),
                () -> assertThat(byQuestionIdAndDeletedFalse.get(0)).isEqualTo(save)
        );
    }

    @Test
    void findByIdAndDeletedFalse() {
        User user = userRepository.save(new User("aaa", "1234", "jurl", "dbswnfl2"));
        Question question = questionRepository.save(new Question("title", "content"));
        Answer answer = new Answer(user, question, "content");
        Answer save = answerRepository.save(answer);

        Optional<Answer> byIdAndDeletedFalse = answerRepository.findByIdAndDeletedFalse(save.getId());

        byIdAndDeletedFalse.ifPresent(
                it -> assertThat(it).isEqualTo(answer)
        );
    }
}
