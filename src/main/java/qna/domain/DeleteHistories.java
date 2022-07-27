package qna.domain;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {

    private List<DeleteHistory> values;

    private DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.values = deleteHistories;
    }

    public static DeleteHistories of(Question question) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        addQuestion(question, deleteHistories);
        addAnswers(question.getAnswers(), deleteHistories);
        return new DeleteHistories(deleteHistories);
    }

    private static void addQuestion(Question question, List<DeleteHistory> deleteHistories) {
        DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter());
        deleteHistories.add(deleteHistory);
    }

    private static void addAnswers(List<Answer> answers, List<DeleteHistory> deleteHistories) {
        for (Answer answer : answers) {
            answer.setDeleted(true);
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter()));
        }
    }

    public List<DeleteHistory> getValues() {
        return values;
    }
}
