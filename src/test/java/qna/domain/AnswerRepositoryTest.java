package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    private User testUser = new User(1L, "test_user", "test_password", "사용자1", "user@gmail.com");
    private Question testQuestion = new Question(1L, "시험용 질문입니다.", "시험용 질문의 내용입니다.");

    @DisplayName("Answer를 저장")
    @Test
    void save() {
        Answer expected = new Answer(testUser, testQuestion, "질문에 대한 답변입니다.");

        Answer actual = answers.save(expected);

        assertThat(expected).isEqualTo(actual);
    }

    @DisplayName("Question Id가 알치하고 삭제되지 않은 모든 Answer 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer expectedIncluded = new Answer(testUser, testQuestion, "질문에 대한 답변입니다.");
        answers.save(expectedIncluded);

        Answer expectedNotIncluded = new Answer(testUser, testQuestion, "질문에 대한 답변입니다.");
        expectedNotIncluded.setDeleted(true);
        answers.save(expectedNotIncluded);

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(testQuestion.getId());

        assertAll(
                () -> assertThat(actual.contains(expectedIncluded)).isTrue(),
                () -> assertThat(actual.contains(expectedNotIncluded)).isFalse()
        );
    }

    @DisplayName("Id가 일치하고 삭제되지 않은 Answer를 조회하고, 값이 존재")
    @Test
    void findByIdAndDeletedFalse_resultExist() {
        Answer expect = new Answer(testUser, testQuestion, "질문에 대한 답변입니다.");
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
        Answer expectNotFound = new Answer(testUser, testQuestion, "질문에 대한 답변입니다.");
        expectNotFound.setDeleted(true);
        Answer savedAnswer = answers.save(expectNotFound);

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(savedAnswer.getId());

        assertThat(actual).isEmpty();
    }
}
