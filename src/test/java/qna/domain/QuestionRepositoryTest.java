package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private final User user = new User("alexzz", "password", "알렉스", "alex@alex.com");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @DisplayName("질문을 저장한다.")
    void save() {
        final String questionTitle = "JPA 어케 잘하죠";
        final String questionContents = "제곧내";
        final Question question = new Question(questionTitle, questionContents);
        question.writeBy(user);

        final Question saved = questionRepository.save(question);

        assertAll(
                () -> assertThat(saved).extracting("title", "contents", "writer", "deleted")
                        .containsExactly(questionTitle, questionContents, user, false),
                () -> assertThat(saved).isSameAs(question)
        );
    }
}