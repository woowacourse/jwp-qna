package qna.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;
import qna.domain.deletehistory.DeleteHistory;
import qna.domain.question.Question;
import qna.domain.question.QuestionRepository;
import qna.domain.user.User;

@Service
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private final QuestionRepository questions;
    private final DeleteHistoryService deleteHistoryService;

    public QnaService(QuestionRepository questions, DeleteHistoryService deleteHistoryService) {
        this.questions = questions;
        this.deleteHistoryService = deleteHistoryService;
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) throws CannotDeleteException {
        Question question = findQuestionById(questionId);
        question.checkOwner(loginUser);

        List<DeleteHistory> deleteHistories = question.delete(loginUser);
        deleteHistoryService.saveAll(deleteHistories);
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questions.findByIdAndDeletedFalse(id)
            .orElseThrow(NotFoundException::new);
    }
}
