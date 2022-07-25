package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
class AnswerRepositoryTest {

    private final UserRepository users;
    private final QuestionRepository questions;
    private final AnswerRepository answers;

    AnswerRepositoryTest(UserRepository users, QuestionRepository questions, AnswerRepository answers) {
        this.users = users;
        this.questions = questions;
        this.answers = answers;
    }

    @DisplayName("Answer를 저장")
    @Test
    void save() {
        User user = users.save((new User("user", "password", "사용자", "user@gmail.com")));
        Question question = questions.save(new Question("질문입니다.", "질문의 내용입니다.").writeBy(user));

        Answer expected = new Answer(user, question, "질문에 대한 답변입니다.");

        Answer actual = answers.save(expected);

        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("Question Id가 알치하고 삭제되지 않은 모든 Answer 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = users.save((new User("user", "password", "사용자", "user@gmail.com")));
        Question question = questions.save(new Question("질문입니다.", "질문의 내용입니다.").writeBy(user));

        Answer expectedIncluded = new Answer(user, question, "질문에 대한 답변입니다.");
        answers.save(expectedIncluded);

        Answer expectedNotIncluded = new Answer(user, question, "질문에 대한 삭제 될 답변입니다.");
        expectedNotIncluded.delete();
        answers.save(expectedNotIncluded);

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(question.getId());

        assertAll(
                () -> assertThat(actual.contains(expectedIncluded)).isTrue(),
                () -> assertThat(actual.contains(expectedNotIncluded)).isFalse()
        );
    }

    @DisplayName("Id가 일치하고 삭제되지 않은 Answer를 조회하고, 값이 존재")
    @Test
    void findByIdAndDeletedFalse_resultExist() {
        User user = users.save((new User("user", "password", "사용자", "user@gmail.com")));
        Question question = questions.save(new Question("질문입니다.", "질문의 내용입니다.").writeBy(user));

        Answer expect = new Answer(user, question, "질문에 대한 답변입니다.");
        Answer saved = answers.save(expect);

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(saved.getId());

        assertAll(
                () -> assertThat(actual).isPresent(),
                () -> assertThat(actual.get()).isEqualTo(expect)
        );
    }

    @DisplayName("Id가 일치하고 삭제되지 않은 Answer를 조회하고, 값이 존재하지 않음")
    @Test
    void findByIdAndDeletedFalse_resultDoesNotExist() {
        User user = users.save((new User("user", "password", "사용자", "user@gmail.com")));
        Question question = questions.save(new Question("질문입니다.", "질문의 내용입니다.").writeBy(user));

        Answer expectNotFound = new Answer(user, question, "질문에 대한 삭제 될 답변입니다.");
        expectNotFound.delete();
        Answer savedAnswer = answers.save(expectNotFound);

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(savedAnswer.getId());

        assertThat(actual).isEmpty();
    }
}
