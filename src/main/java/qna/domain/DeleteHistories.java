package qna.domain;

import java.util.ArrayList;
import java.util.List;

public class DeleteHistories {

    private final List<DeleteHistory> values;

    public DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.values = deleteHistories;
    }

    public DeleteHistories() {
        this.values = new ArrayList<>();
    }

    public static DeleteHistories from(DeleteHistory questionDeleteHistory, DeleteHistories answerDeleteHistories) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(questionDeleteHistory);
        deleteHistories.addAll(answerDeleteHistories.getValues());
        return new DeleteHistories(deleteHistories);
    }

    public List<DeleteHistory> getValues() {
        return values;
    }

    public void add(DeleteHistory deleteHistory) {
        this.values.add(deleteHistory);
    }
}
