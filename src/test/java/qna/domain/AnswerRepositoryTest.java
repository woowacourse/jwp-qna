package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import qna.JpaConfig;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JpaConfig.class)
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Test
    void Answer를_저장하고_조회한다() {
        Answer answer = AnswerTest.A1;
        System.out.println("not saved");
        System.out.println(answer.getId());
        answerRepository.save(answer);
        System.out.println("saved");
        System.out.println(answer.getId());
        Answer saved = answerRepository.findById(answer.getId()).get();

        assertThat(answer).isSameAs(saved);
    }
}
