package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    @Test
    void saveQuestion() {
        Question savedQuestion = questionRepository.save(Q1);
        System.out.println(savedQuestion.getCreatedAt());
        System.out.println(savedQuestion.getUpdatedAt());
        assertThat(savedQuestion.getId()).isNotNull();
    }

    @Test
    void findQuestion() {
        Question savedQuestion = questionRepository.save(Q1);
        Question savedQuestion2 = questionRepository.save(Q2);
        List<Question> answers = questionRepository.findAll();

        assertThat(answers.size()).isEqualTo(2);
        assertThat(answers).contains(savedQuestion,savedQuestion2);
    }

    @Test
    void findQuestionById() {
        Question savedQuestion = questionRepository.save(Q1);
        Question findQuestion = questionRepository.findById(savedQuestion.getId()).get();

        assertThat(findQuestion).isEqualTo(savedQuestion);
    }
}
