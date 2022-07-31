package qna.domain;

import java.util.List;

public class DeleteHistories {

    private final List<DeleteHistory> deleteHistories;

    public DeleteHistories(List<DeleteHistory> deleteHistories) {
        this.deleteHistories = deleteHistories;
    }

    public void deleteQuestion(Question question) {
        if (question.isDeleted()) {
            return;
        }
        question.setDeleted(true);
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter()));
    }

    public void deleteAnswers(Answers answers) {
        for (Answer answer : answers.getAnswers()) {
            answer.setDeleted(true);
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter()));
        }
    }

    public List<DeleteHistory> getDeleteHistories() {
        return deleteHistories;
    }
}
