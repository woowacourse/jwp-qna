package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.answer.Answer;
import qna.domain.answer.AnswerRepository;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionTest {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private UserRepository users;

    @Autowired
    private EntityManager entityManager;

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
}
