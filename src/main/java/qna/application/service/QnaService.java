package qna.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.content.ContentType;
import qna.domain.content.answer.Answer;
import qna.domain.content.answer.AnswerRepository;
import qna.domain.content.question.Question;
import qna.domain.content.question.QuestionRepository;
import qna.domain.log.DeleteHistory;
import qna.domain.log.DeleteHistoryRepository;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private final QuestionRepository questionRepository;
    private final DeleteHistoryRepository deleteHistoryRepository;

    public QnaService(QuestionRepository questionRepository,
                      DeleteHistoryRepository deleteHistoryRepository) {
        this.questionRepository = questionRepository;
        this.deleteHistoryRepository = deleteHistoryRepository;
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) throws CannotDeleteException {
        Question question = findQuestionById(questionId);
        List<DeleteHistory> deleteHistories = question.deleteBy(loginUser, LocalDateTime.now());

        deleteHistoryRepository.saveAll(deleteHistories);
    }
}
