package qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.domain.Answer;
import qna.domain.AnswerRepository;
import qna.domain.ContentType;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.domain.User;
import qna.fixtures.UserFixture;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {

    @Mock
    private AnswerRepository answers;

    @Mock
    private QuestionRepository questions;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    @DisplayName("질문 삭제")
    @Test
    void deleteQuestion() {
        User user = UserFixture.JAVAJIGI.generate(1L);
        Question question = new Question(1L, "title", "contents").writeBy(user);
        Answer answer = new Answer(1L, user, question, "Answers Contents1");
        question.addAnswer(answer);

        when(questions.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        qnaService.deleteQuestion(user, question.getId());

        assertThat(question.isDeleted()).isTrue();

        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter()),
                new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter())
        );

        verify(deleteHistoryService).saveAll(deleteHistories);
    }
    
    @DisplayName("답변 삭제")
    @Test
    void deleteAnswer() {
        User user = UserFixture.JAVAJIGI.generate(1L);
        Question question = new Question(1L, "title", "contents").writeBy(user);
        Answer answer = new Answer(1L, user, question, "Answers Contents1");

        when(answers.findByIdAndDeletedFalse(answer.getId())).thenReturn(Optional.of(answer));
        qnaService.deleteAnswer(user, answer.getId());

        assertThat(answer.isDeleted()).isTrue();

        DeleteHistory deleteHistory = new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter());

        verify(deleteHistoryService).save(deleteHistory);
    }
}
