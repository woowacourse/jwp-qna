package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("저장 후 id로 조회가 가능하다.")
    @Test
    void findById() {
        Answer expected = new Answer(LocalDateTime.now(), false, 1L, LocalDateTime.now(),
                1L, "contents");
        Answer actual = answerRepository.save(expected);
        assertThat(actual).isEqualTo(expected);
    }
}
