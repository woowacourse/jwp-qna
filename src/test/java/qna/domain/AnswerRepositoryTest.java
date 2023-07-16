package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;


class AnswerRepositoryTest extends RepositoryTest {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerRepositoryTest(final AnswerRepository answerRepository, final QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Test
    void findByQuestionIdAndDeletedFalse() {
        final Question question = new Question("제목", "내용");
        final Answer answer = new Answer("answer");
        question.addAnswer(answer);
        questionRepository.save(question);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(1L);

        assertThat(answers).isNotEmpty();
    }

    @Test
    void findByIdAndDeletedFalse() {
        Answer answer = new Answer("answer");
        Answer savedAnswer = answerRepository.save(answer);
        Answer findAnswer = answerRepository.findByIdAndDeletedFalse(savedAnswer.getId()).get();

        assertThat(findAnswer).isSameAs(answer);
    }
}
