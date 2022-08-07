package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.answer.Answer;
import qna.domain.answer.AnswerRepository;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.user.User;
import qna.domain.user.UserRepository;
import qna.exception.AlreadyDeletedException;
import qna.exception.CannotDeleteException;
import qna.annotation.DatabaseTest;

@SuppressWarnings("NonAsciiCharacters")
@DatabaseTest
class QuestionTest {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private UserRepository users;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("findAll 메서드 호출시, SELECT문 실행 여부 및 대상 검증")
    @Nested
    class FindAllTest {

        private User user;
        private Question newQuestion;

        @BeforeEach
        void setUp() {
            user = users.save(new User("javajigi", "password", "name", "javajigi@slipp.net"));
            newQuestion = questions.save(new Question("title1", "contents1", user));
        }

        @Test
        void getWriterId_메서드처럼_클래스_내부에서_연관관계를_호출하려는_경우에도_SELECT문_실행하여_지연로딩_발생() {
            List<Question> allQuestions = questions.findAll();
            Question question = allQuestions.get(0);

            Long writerId = question.getWriterId();
            assertThat(writerId).isNotNull();
        }

        @Test
        void 개별_answer에서_연관된_question_접근하여_조회_및_수정_가능() {
            answers.save(new Answer(user, newQuestion, "contents1"));
            answers.saveAndFlush(new Answer(user, newQuestion, "contents2"));
            entityManager.clear(); // 영속성 컨텍스틀르 비워줘야 제대로 SELECT문들 실행

            Answer answer = answers.findAll().get(0);

            Question question = answer.getQuestion();
            assertThat(question).isNotNull();
        }

        @Test
        void question에서_연관된_answers_조회_가능() {
            answers.save(new Answer(user, newQuestion, "contents1"));
            answers.saveAndFlush(new Answer(user, newQuestion, "contents2"));
            entityManager.clear();

            Question question = questions.findAll().get(0);

            assertThat(question.getAnswers()).hasSize(2);
        }

        @Test
        void flush를_실행하지_않아_이미_영속성_컨텍스트에_연관관계가_존재하는_경우_별도로_SELECT문을_실행하지_않으므로_로딩되지_않음() {
            answers.save(new Answer(user, newQuestion, "contents1"));
            answers.saveAndFlush(new Answer(user, newQuestion, "contents2"));

            Question question = questions.findAll().get(0);

            assertThat(question.getAnswers()).isEmpty();
        }

        @Test
        void question에서_연관된_answers_중_deleted_값이_false인_데이터는_조회대상에서_자동으로_누락() {
            Answer deletedAnswer = new Answer(user, newQuestion, "deleted content");
            deletedAnswer.deleteBy(user);
            answers.save(new Answer(user, newQuestion, "contents"));
            answers.saveAndFlush(deletedAnswer);
            entityManager.clear();

            Question question = questions.findAll().get(0);

            assertThat(question.getAnswers()).hasSize(1);
        }
    }

    @DisplayName("delete 메서드 검증")
    @Nested
    class DeleteByTest {

        @Test
        void 현재_데이터를_삭제된_상태로_변경() {
            User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
            Question question = new Question(1L, "title2", "contents2", user);

            question.deleteBy(user);

            assertThat(question.isDeleted()).isEqualTo(true);
        }

        @Test
        void 현재_데이터_자체와_연관된_데이터에_대한_DeleteHistory_리스트_반환() {
            User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
            Question question = new Question(1L, "title2", "contents2", user);
            Answer answer1 = new Answer(1L, user, question, "contents1");
            Answer answer2 = new Answer(2L, user, question, "contents2");
            question.addAnswer(answer1);
            question.addAnswer(answer2);

            List<DeleteHistory> actual = question.deleteBy(user);
            List<DeleteHistory> expected = List.of(
                    DeleteHistory.ofAnswer(answer1.getId(), answer1.getWriter()),
                    DeleteHistory.ofAnswer(answer2.getId(), answer2.getWriter()),
                    DeleteHistory.ofQuestion(question.getId(), question.getWriter()));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 다른_사람이_쓴_글을_제거하려는_경우_예외발생() {
            User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
            Question question = new Question(1L, "title2", "contents2", user);
            User someoneElse = new User(1L, "kotlinjigi", "password", "jason", "jason@slipp.net");

            assertThatThrownBy(() -> question.deleteBy(someoneElse))
                    .isInstanceOf(CannotDeleteException.class);
        }

        @Test
        void 답변_중_작성자_이외의_사람이_쓴_글이_포함된_경우_예외발생() {
            User pobi = new User(1L, "javajigi", "password", "pobi", "javajigi@slipp.net");
            User jason = new User(2L, "kotlinjigi", "password", "jason", "jason@slipp.net");
            Question question = new Question(1L, "title2", "contents2", pobi);
            Answer answer = new Answer(1L, jason, question, "Answers Contents1");
            question.addAnswer(answer);

            assertThatThrownBy(() -> question.deleteBy(pobi))
                    .isInstanceOf(CannotDeleteException.class);
        }

        @Test
        void 이미_삭제된_경우_예외발생() {
            User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
            Question question = new Question(1L, "title2", "contents2", user);

            question.deleteBy(user);

            assertThatThrownBy(() -> question.deleteBy(user))
                    .isInstanceOf(AlreadyDeletedException.class);
        }
    }
}
