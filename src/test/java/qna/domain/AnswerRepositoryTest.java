package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
class AnswerRepositoryTest extends RepositoryTest {

    private static Answer answer;
    private static Question savedQuestion;
    private static User savedJavajigi;

    private final AnswerRepository answers;
    private final QuestionRepository questions;
    private final UserRepository users;

    public AnswerRepositoryTest(AnswerRepository answers, QuestionRepository questions, UserRepository users) {
        this.answers = answers;
        this.questions = questions;
        this.users = users;
    }

    @BeforeEach
    void setUp() {
        User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        Question question = new Question("제목", "내용");
        answer = new Answer(javajigi, question, "답변");

        savedQuestion = questions.save(question);
        answer.toQuestion(savedQuestion);

        savedJavajigi = users.save(javajigi);
        answer.setWriter(savedJavajigi);
    }

    @DisplayName("답변 생성")
    @Test
    void save() {
        Answer expected = answer;

        Answer actual = answers.save(expected);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("답변 조회")
    @Test
    void findById() {
        Answer expected = answers.save(answer);
        synchronize();

        Optional<Answer> actual = answers.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("삭제되지 않은 답변 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Answer expected = answers.save(answer);
        synchronize();

        Optional<Answer> actual = answers.findByIdAndDeletedFalse(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("질문에 속하는 답변 목록 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        Answer answer1 = answers.save(new Answer(savedJavajigi, savedQuestion, "답변1"));
        Answer answer2 = answers.save(new Answer(savedJavajigi, savedQuestion, "답변2"));
        Answer answer3 = answers.save(new Answer(savedJavajigi, savedQuestion, "답변3"));
        List<Answer> expected = List.of(answer1, answer2, answer3);
        synchronize();

        List<Answer> actual = answers.findByQuestionIdAndDeletedFalse(savedQuestion.getId());

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @DisplayName("답변 수정")
    @Test
    void update() {
        Answer savedAnswer = answers.save(answer);
        Answer expected = new Answer(savedJavajigi, savedQuestion, "답변 수정");

        savedAnswer.update(expected);
        synchronize();

        Optional<Answer> actual = answers.findById(savedAnswer.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringFields("createDate")
                .ignoringFields("updateDate")
                .isEqualTo(expected);
    }

    @DisplayName("답변 삭제")
    @Test
    void delete() {
        Answer deletedAnswer = answers.save(answer);
        synchronize();

        answers.delete(deletedAnswer);
        synchronize();

        assertThat(answers.findAll()).hasSize(0);
    }
}
