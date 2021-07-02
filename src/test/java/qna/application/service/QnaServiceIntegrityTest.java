package qna.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.content.answer.Answer;
import qna.domain.content.answer.AnswerRepository;
import qna.domain.content.answer.TestAnswer;
import qna.domain.content.question.Question;
import qna.domain.content.question.QuestionRepository;
import qna.domain.content.question.TestQuestion;
import qna.domain.user.TestUser;
import qna.domain.user.User;
import qna.domain.user.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class QnaServiceIntegrityTest {

    @Autowired
    private QnaService qnaService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

    private Question question;
    private User writer;
    private List<Answer> answers;

    @BeforeEach
    void setUp() {
        writer = userRepository.save(TestUser.create());
        answers = answerRepository.saveAll(
                Arrays.asList(
                        TestAnswer.create(writer),
                        TestAnswer.create(writer),
                        TestAnswer.create(writer),
                        TestAnswer.create(writer)
                )
        );

        question = questionRepository.save(TestQuestion.create(writer));
        answers.forEach(question::addAnswer);
    }

    @DisplayName("id로 질문을 검색한다.")
    @Test
    void findQuestionById() {
        Question savedQuestion = qnaService.findQuestionById(question.getId());

        assertThat(savedQuestion)
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @DisplayName("질문을 삭제한다.")
    @Test
    void deleteQuestion() {
        qnaService.deleteQuestion(writer, question.getId());

        assertThat(
            questionRepository.findByIdAndDeletedFalse(question.getId()).isPresent()
        ).isFalse();

        answers.forEach(answer ->
            assertThat(
                    answerRepository.findByIdAndDeletedFalse(answer.getId()).isPresent()
            ).isFalse()
        );
    }

    @DisplayName("질문을 삭제시, 실제 데이터는 삭제하지 않고 삭제 표식만 남긴다.")
    @Test
    void deleteQuestion_remainDataInDB() {
        qnaService.deleteQuestion(writer, question.getId());

        assertThat(
                questionRepository.findById(question.getId()).isPresent()
        ).isTrue();

        answers.forEach(answer ->
                assertThat(
                        answerRepository.findById(answer.getId()).isPresent()
                ).isTrue()
        );
    }
}