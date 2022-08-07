package qna.domain.answer;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.user.UserTest.JAVAJIGI;
import static qna.domain.user.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import qna.config.DatabaseConfig;
import qna.domain.question.Question;
import qna.domain.question.QuestionRepository;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Question question1;
    private Question question2;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = users.save(JAVAJIGI);
        user2 = users.save(SANJIGI);
        question1 = questions.save(new Question(1L, "title1", "contents1", user1));
        question2 = questions.save(new Question(2L, "title2", "contents2", user2));
    }

    /**
     * JPA 엔티티 위에 @Where(clause = "deleted=false")를 붙였으므로, 디폴트로 deleted 값이 거짓인 데이터만 조회됨
     */
    @Test
    void findByQuestion_메서드는_questionId_값이_인자값과_동일하며_deleted_값이_거짓인_데이터의_리스트_반환() {
        final Answer 자바지기의_답글 = new Answer(user1, question1, "Answers Contents1");
        final Answer 산지기의_답글 = new Answer(user2, question2, "Answers Contents2");
        final Answer 산지기의_답글2 = new Answer(user2, question2, "Answers Contents3");
        answers.saveAll(List.of(자바지기의_답글, 산지기의_답글, 산지기의_답글2));

        List<Answer> actual = answers.findByQuestion(question1);
        List<Answer> expected = List.of(자바지기의_답글);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findById_메서드는_id_값이_인자값과_동일하며_deleted_값이_거짓인_데이터를_Optional로_반환() {
        final Answer 자바지기의_답글 = new Answer(user1, question1, "Answers Contents1");
        answers.save(자바지기의_답글);

        Optional<Answer> actual1 = answers.findById(자바지기의_답글.getId());
        Optional<Answer> expected1 = Optional.of(자바지기의_답글);
        assertThat(actual1).isEqualTo(expected1);

        Optional<Answer> actual2 = answers.findById(9999L);
        Optional<Answer> expected2 = Optional.ofNullable(null);
        assertThat(actual2).isEqualTo(expected2);
    }

    /**
     * JPA 엔티티 위의 @SQLDelete를 통해 delete 메서드 호출시 다른 SQL문이 실행되도록 덮어씀
     */
    @Test
    void delete_메서드는_데이터를_삭제하지_않고_deleted_컬럼의_값만_수정() {
        final Answer 답 = new Answer(user1, question1, "contents1");
        answers.save(답);

        assertThat(답.isDeleted()).isFalse();
        answers.delete(답);
        answers.flush();

        boolean actual = jdbcTemplate.queryForObject("SELECT deleted FROM answer WHERE id = " + 답.getId(), Boolean.class);
        assertThat(actual).isTrue();
    }
}
