package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private Question question1;
    private Question question2;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("changer", "password", "name", "changer@back.end"));
        question1 = questionRepository.save(new Question("title1", "contents1").writeBy(user1));
        question2 = questionRepository.save(new Question("title2", "contents2").writeBy(user1));
    }

    @Test
    void 질문ID를_입력_받아_삭제되지_않은_답변을_조회한다() {
        // given
        answerRepository.save(new Answer(user1, question1, "Answers Contents1"));
        Answer answer = answerRepository.save(new Answer(user1, question1, "Answers Contents2"));
        answer.delete();

        Answer diffrentQuestionIdAnswer = new Answer(user1, question2, "Answers Contents3");
        answerRepository.save(diffrentQuestionIdAnswer);

        // when
        List<Answer> results = answerRepository.findByQuestionIdAndDeletedFalse(question1.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(results).hasSize(1);
            softly.assertThat(results).doesNotContain(diffrentQuestionIdAnswer);
        });
    }

    @Test
    void ID를_입력_받아_삭제되지_않은_답변을_조회한다() {
        // given
        Answer answer = answerRepository.save(new Answer(user1, question1, "Answers Contents1"));

        // when
        Optional<Answer> byIdAndDeletedFalse = answerRepository.findByIdAndDeletedFalse(answer.getId());

        // then
        assertThat(byIdAndDeletedFalse).isPresent();
    }
}
