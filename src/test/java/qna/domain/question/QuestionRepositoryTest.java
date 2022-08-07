package qna.domain.question;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.user.UserTest.JAVAJIGI;
import static qna.domain.user.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import qna.config.DatabaseConfig;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * JPA 엔티티 위에 @Where(clause = "deleted=false")를 붙였으므로, 디폴트로 deleted 값이 거짓인 데이터만 조회됨
     */
    @Test
    void findAll_메서드는_deleted_값이_거짓인_데이터의_리스트_반환() {
        List<User> newUsers = users.saveAll(List.of(JAVAJIGI, SANJIGI));
        final Question 문제1 = new Question("title1", "contents1", newUsers.get(0));
        final Question 문제2 = new Question("title2", "contents2", newUsers.get(1));
        문제2.setDeleted(true);
        questions.saveAll(List.of(문제1, 문제2));

        List<Question> actual = questions.findAll();
        List<Question> expected = List.of(문제1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findById_메서드는_id_값이_인자값과_동일하며_deleted_값이_거짓인_데이터를_Optional로_반환() {
        User 자바지기 = users.save(JAVAJIGI);
        final Question 문제 = new Question("title1", "contents1", 자바지기);
        questions.save(문제);

        Optional<Question> actual1 = questions.findById(문제.getId());
        Optional<Question> expected1 = Optional.of(문제);
        assertThat(actual1).isEqualTo(expected1);

        Optional<Question> actual2 = questions.findById(9999L);
        Optional<Question> expected2 = Optional.ofNullable(null);
        assertThat(actual2).isEqualTo(expected2);
    }

    /**
     * JPA 엔티티 위의 @SQLDelete를 통해 delete 메서드 호출시 다른 SQL문이 실행되도록 덮어씀
     */
    @Test
    void delete_메서드는_데이터를_삭제하지_않고_deleted_컬럼의_값만_수정함() {
        User newUser = users.save(JAVAJIGI);
        final Question 문제 = new Question("title1", "contents1", newUser);
        questions.save(문제);

        assertThat(문제.isDeleted()).isFalse();
        questions.delete(문제);
        questions.flush();

        boolean actual = jdbcTemplate.queryForObject("SELECT deleted FROM question WHERE id = " + 문제.getId(),
                Boolean.class);
        assertThat(actual).isTrue();
    }
}
