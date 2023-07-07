package qna.domain;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuestionTest {

    public static final Question Q1 = new Question("title1", "contents1");
    public static final Question Q2 = new Question("title2", "contents2");

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = userRepository.save(UserTest.JAVAJIGI);
        Q1.writeBy(user1);
        User user2 = userRepository.save(UserTest.SANJIGI);
        Q2.writeBy(user2);
    }

    @Test
    void 질문을_저장한다() {
        // given, when
        Question savedQuestion = questionRepository.save(Q1);

        // then
        assertThat(savedQuestion.getId()).isNotNull();
    }

    @Test
    void 질문을_찾는다() {
        // given, when
        Question savedQuestion1 = questionRepository.save(Q1);
        Question savedQuestion2 = questionRepository.save(Q2);

        List<Question> questions = questionRepository.findAll();

        // then
        assertThat(questions).containsExactlyInAnyOrder(savedQuestion1, savedQuestion2);
    }

    @Test
    void id로_질문을_찾는다() {
        // given, when
        Question savedQuestion = questionRepository.save(Q1);
        Question findQuestion = questionRepository.findById(savedQuestion.getId()).get();

        // then
        AssertionsForClassTypes.assertThat(savedQuestion).isEqualTo(findQuestion);
    }
}
