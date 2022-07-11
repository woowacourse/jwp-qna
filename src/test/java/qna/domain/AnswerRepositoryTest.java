package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void save() {
        Answer answer = new Answer(JAVAJIGI, Q1, "AnswerContent");

        Answer savedAnswer = answerRepository.save(answer);

        assertThat(savedAnswer).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(answer);
    }

    @Test
    void findById() {
        Answer answer = new Answer(JAVAJIGI, Q1, "AnswerContent");
        Answer savedAnswer = answerRepository.save(answer);

        Optional<Answer> result = answerRepository.findById(answer.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedAnswer);
    }

    @Test
    void findAll() {
        Answer answer1 = new Answer(JAVAJIGI, Q1, "AnswerContent");
        Answer savedAnswer1 = answerRepository.save(answer1);
        Answer answer2 = new Answer(SANJIGI, Q2, "AnswerContent");
        Answer savedAnswer2 = answerRepository.save(answer2);

        List<Answer> result = answerRepository.findAll();

        assertThat(result).contains(savedAnswer1, savedAnswer2);
    }

    @Test
    void deleteById() {
        Answer answer = new Answer(JAVAJIGI, Q1, "AnswerContent");
        Answer savedAnswer = answerRepository.save(answer);

        answerRepository.deleteById(savedAnswer.getId());
        Optional<Answer> result = answerRepository.findById(savedAnswer.getId());

        assertThat(result).isEmpty();
    }
}
