package qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.domain.Answer;
import qna.domain.ContentType;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.QuestionRepository;
import qna.domain.User;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    @Test
    public void delete_성공() throws Exception {
        User javajigi = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        User sanjigi = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

        Question question = new Question(1L, "title", "contents").writeBy(javajigi);
        Answer answer = new Answer(1L, sanjigi, question, "Answers Contents1");
        question.addAnswer(answer);
        answer.deleteBy(sanjigi);

        when(questionRepository.findByIdAndDeletedFalse(question.getId())).thenReturn(Optional.of(question));
        qnaService.deleteQuestion(javajigi, question.getId());

        assertThat(question.isDeleted()).isTrue();

        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now())
        );

        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
