package qna.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.answer.Answer;
import qna.domain.answer.AnswerRepository;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository users;

    @Test
    void findByUserId_메서드는_userId_값이_인자값과_동일한_데이터를_Optional로_반환() {
        final User 코틀린지기 = new User("kotlinjigi", "password", "jason", "jason@slipp.net");
        users.save(코틀린지기);

        Optional<User> actual1 = users.findByUserId("kotlinjigi");
        Optional<User> expected1 = Optional.of(코틀린지기);
        assertThat(actual1).isEqualTo(expected1);

        Optional<User> actual2 = users.findByUserId("존재하지_않는_자바지기");
        Optional<User> expected2 = Optional.ofNullable(null);
        assertThat(actual2).isEqualTo(expected2);
    }
}
