package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private final User user
            = new User("yxxnghwan", "password", "알렉스", "younghwan960@gmail.com");
    private final Question question = new Question("JPA 어케 잘하죠", "제곧내");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        question.writeBy(user);
        questionRepository.save(question);
    }

    @Test
    @DisplayName("answer를 저장한다.")
    void save() {
        final Answer answer = new Answer(user, question, "제이슨 보고 배우면 됩니다!");

        final Answer saved = answerRepository.save(answer);

        assertThat(saved).extracting("writerId", "questionId", "contents")
                .containsExactly(user.getId(), question.getId(), answer.getContents());
    }
}