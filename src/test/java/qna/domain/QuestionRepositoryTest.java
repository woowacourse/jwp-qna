package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    User user;
    Question question;

    @BeforeEach
    void setUp() {
        question = questionRepository.save(new Question("title1", "content1").writeBy(user));
        user = userRepository.save(new User("changer", "password", "name", "changer@back.end"));
    }

    @Test
    void 삭제되지_않은_질문을_조회한다() {
        // given
        question.deleteBy(user);
        questionRepository.save(new Question("title2", "content2").writeBy(user));

        // when
        List<Question> results = questionRepository.findByDeletedFalse();

        // then
        assertThat(results).hasSize(1);
    }

    @Test
    void ID를_입력_받아_삭제되지_않은_질문을_조회한다() {
        // when
        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(question.getId());

        // then
        assertThat(result).isPresent();
    }
}
