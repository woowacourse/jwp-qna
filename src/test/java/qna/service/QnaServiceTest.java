package qna.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.domain.Answer;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.exception.CannotDeleteException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static qna.fixture.Fixture.*;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    @Test
    public void delete_성공() {
        Question question = new Question(1L, "title1", JAVAJIGI,"contents1");
        Answer answer = question.addAnswer(JAVAJIGI, question, "Answers Contents1");

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThat(question.isDeleted()).isFalse();
        qnaService.deleteQuestion(JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        verifyDeleteHistories(question, answer);
    }

    @Test
    public void delete_다른_사람이_쓴_글() {
        Question question = new Question(1L, "title1", JAVAJIGI, "contents1");
        question.addAnswer(JAVAJIGI, question, "Answers Contents1");

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(SANJIGI, question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() {
        Question question = new Question(1L, "title1", JAVAJIGI, "contents1");
        Answer answer = question.addAnswer(JAVAJIGI, question, "Answers Contents1");

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        qnaService.deleteQuestion(JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        verifyDeleteHistories(question, answer);
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Question question = new Question(1L, "title1", JAVAJIGI,"contents1");
        question.addAnswer(SANJIGI, Q1, "Answers Contents1");

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(JAVAJIGI, question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories(Question question, Answer answer) {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                DeleteHistory.ofQuestion(question.getId(), JAVAJIGI),
                DeleteHistory.ofAnswer(answer.getId(), JAVAJIGI)
        );
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
