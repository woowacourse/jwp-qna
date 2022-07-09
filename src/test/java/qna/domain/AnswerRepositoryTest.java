package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private UserRepository users;

    @Autowired
    private QuestionRepository questions;

    @BeforeEach
    void setUp() {
        answers.deleteAll();
    }

    @DisplayName("답변을 저장한다.")
    @Test
    void save() {
        User user = users.save(new User("알린", "12345678", "장원영", "ozragwort@gmail.com"));
        Question question = questions.save(new Question("title", "questionContents"));
        String contents = "answerContents";
        Answer answer = new Answer(user, question, contents);

        Answer actual = answers.save(answer);

        assertThat(actual == answer).isTrue();
    }

    @DisplayName("questionId로 deleted가 false인 답변을 찾는다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = users.save(new User("알린", "12345678", "장원영", "ozragwort@gmail.com"));
        Question question = questions.save(new Question("title", "questionContents"));
        String contents = "answerContents";
        Answer answer = answers.save(new Answer(user, question, contents));

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(question.getId());

        assertThat(actual).hasSize(1);
    }

    @DisplayName("id로 deleted가 false인 답변을 찾는다.")
    @Test
    void findByIdAndDeletedFalse() {
        User user = users.save(new User("알린", "12345678", "장원영", "ozragwort@gmail.com"));
        Question question = questions.save(new Question("title", "questionContents"));
        String contents = "answerContents";
        Answer answer = answers.save(new Answer(user, question, contents));

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(answer.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get().getContents()).isEqualTo(contents)
        );
    }
}
