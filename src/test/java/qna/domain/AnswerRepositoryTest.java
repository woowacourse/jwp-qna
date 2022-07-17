package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest extends RepositoryTest {

    @DisplayName("Answer를 저장")
    @Test
    void save() {
        User user = saveUser();
        Question question = saveQuestion(user);

        Answer expected = new Answer(user, question, "질문에 대한 답변입니다.");

        Answer actual = answers.save(expected);

        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("Question Id가 알치하고 삭제되지 않은 모든 Answer 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User user = saveUser();
        Question question = saveQuestion(user);

        Answer expectedIncluded = new Answer(user, question, "질문에 대한 답변입니다.");
        answers.save(expectedIncluded);

        Answer expectedNotIncluded = new Answer(user, question, "질문에 대한 답변입니다.");
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
        User user = saveUser();
        Question question = saveQuestion(user);

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
        User user = saveUser();
        Question question = saveQuestion(user);

        Answer expectNotFound = new Answer(user, question, "질문에 대한 답변입니다.");
        expectNotFound.delete();
        Answer savedAnswer = answers.save(expectNotFound);

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(savedAnswer.getId());

        assertThat(actual).isEmpty();
    }
}
