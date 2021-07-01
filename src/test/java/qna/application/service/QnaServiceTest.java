package qna.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.domain.user.TestUser;
import qna.domain.content.answer.Answer;
import qna.domain.content.question.Question;
import qna.domain.content.question.QuestionRepository;
import qna.domain.log.DeleteHistory;
import qna.domain.log.DeleteHistoryRepository;
import qna.domain.user.User;
import qna.exception.CannotDeleteException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryRepository deleteHistoryRepository;

    @InjectMocks
    private QnaService qnaService;

    private Question question;
    private Answer answer;

    private User javajigi;
    private User sanjigi;

    @BeforeEach
    public void setUp() {
        javajigi = TestUser.create();
        sanjigi = TestUser.create();

        question = new Question(1L, javajigi, "title1", "contents1", Collections.emptyList());
        answer = new Answer(1L, javajigi, "Answers Contents1");
        question.addAnswer(answer);
    }

    @Test
    public void delete_성공() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));

        assertThat(question.isDeleted()).isFalse();
        qnaService.deleteQuestion(javajigi, question.getId());

        assertThat(question.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
                .thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(sanjigi, question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() {
        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
                .thenReturn(Optional.of(question));

        qnaService.deleteQuestion(javajigi, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() {
        Answer answer2 = new Answer(2L, sanjigi, "Answers Contents1");
        question.addAnswer(answer2);

        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
                .thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(javajigi, question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(question, javajigi, LocalDateTime.now()),
                new DeleteHistory(answer, javajigi, LocalDateTime.now())
        );
        verify(deleteHistoryRepository).saveAll(deleteHistories);
    }
}
