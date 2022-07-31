package qna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.question.Question;
import qna.domain.question.QuestionRepository;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;

@Service
public class QnaService {

    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private final QuestionRepository questionRepository;
    private final DeleteHistoryService deleteHistoryService;

    public QnaService(QuestionRepository questionRepository,
                      DeleteHistoryService deleteHistoryService) {
        this.questionRepository = questionRepository;
        this.deleteHistoryService = deleteHistoryService;
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) {
        Question question = findQuestionById(questionId);
        validateQuestionMaker(loginUser, question);
        validateQuestionContainsOnlyAuthorAnswers(loginUser, question);

        deleteHistoryService.saveAll(question.delete());
    }

    private void validateQuestionMaker(User loginUser, Question question) {
        if (!question.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private void validateQuestionContainsOnlyAuthorAnswers(User loginUser, Question question) {
        boolean containsNonAuthorAnswer = question.getAnswers()
                .stream()
                .anyMatch(answer -> !answer.isOwner(loginUser));
        if (containsNonAuthorAnswer) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}
