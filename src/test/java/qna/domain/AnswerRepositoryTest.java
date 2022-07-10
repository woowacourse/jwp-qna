package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("deleted가 false인 답변들을 질문 id로 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        final Question question = questionRepository.save(QuestionTest.Q1);

        final List<Answer> answers = answerRepository.saveAll(
                Arrays.asList(new Answer(UserTest.JAVAJIGI, question, "Answers Contents1"),
                new Answer(UserTest.SANJIGI, question, "Answers Contents2"))
        );

        final List<Answer> getAnswers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        assertThat(answers).containsExactlyInAnyOrderElementsOf(getAnswers);
    }

    @DisplayName("deleted가 false인 답변들을 답변 id로 조회 - 존재 O")
    @Test
    void findByIdAndDeletedFalse() {
        userRepository.save(UserTest.JAVAJIGI);
        questionRepository.save(QuestionTest.Q1);

        final Answer answer = answerRepository.save(AnswerTest.A1);

        assertThat(answerRepository.findByIdAndDeletedFalse(answer.getId()).get()).isEqualTo(answer);
    }

    @DisplayName("deleted가 false인 답변들을 답변 id로 조회 - 존재 X")
    @Test
    void findByIdAndDeletedFalseIsNotExist() {
        userRepository.save(UserTest.JAVAJIGI);
        questionRepository.save(QuestionTest.Q1);

        final Answer answer = answerRepository.save(AnswerTest.A1);

        assertThat(answerRepository.findByIdAndDeletedFalse(answer.getId() - 1)).isEmpty();
    }
}
