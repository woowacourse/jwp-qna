package qna.domain;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {

    private final List<DeleteHistory> values;

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
        DeleteHistory deleteHistory = question.deleteSoft();
        deleteHistories.add(deleteHistory);
    }

    private static void addAnswers(List<Answer> answers, List<DeleteHistory> deleteHistories) {
        for (Answer answer : answers) {
            DeleteHistory deleteHistory = answer.deleteSoft();
            deleteHistories.add(deleteHistory);
        }
    }

    public List<DeleteHistory> getValues() {
        return values;
    }
}
