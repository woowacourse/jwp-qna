package qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {

    private List<DeleteHistory> values;

    private DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.values = deleteHistories;
    }

    public static DeleteHistories of(Question question) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, question.getId(),
                question.getWriter(), LocalDateTime.now());
        deleteHistories.add(deleteHistory);

        addAnswersToDeleteHistories(question, deleteHistories);
        return new DeleteHistories(deleteHistories);
    }

    private static void addAnswersToDeleteHistories(Question question, List<DeleteHistory> deleteHistories) {
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            answer.setDeleted(true);
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(),
                    LocalDateTime.now()));
        }
    }

    public List<DeleteHistory> getValues() {
        return values;
    }
}
