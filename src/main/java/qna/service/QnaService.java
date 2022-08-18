package qna.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.NotFoundException;
import qna.domain.Answer;
import qna.domain.AnswerRepository;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.domain.User;

@Service
@Transactional(readOnly = true)
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private final AnswerRepository answers;
    private final QuestionRepository questions;
    private final DeleteHistoryService deleteHistoryService;

    public QnaService(final AnswerRepository answers, final QuestionRepository questions,
                      final DeleteHistoryService deleteHistoryService) {
        this.answers = answers;
        this.questions = questions;
        this.deleteHistoryService = deleteHistoryService;
    }

    public Answer findAnswerById(Long id) {
        return answers.findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    public Question findQuestionById(Long id) {
        return questions.findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteAnswer(User loginUser, Long answerId) {
        Answer answer = findAnswerById(answerId);

        DeleteHistory deleteHistory = answer.deleteBy(loginUser);

        deleteHistoryService.save(deleteHistory);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Question question = findQuestionById(questionId);

        List<DeleteHistory> deleteHistories = question.deleteBy(loginUser);

        deleteHistoryService.saveAll(deleteHistories);
    }
}
