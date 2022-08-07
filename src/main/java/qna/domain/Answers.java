package qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import qna.CannotDeleteException;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question")
    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<DeleteHistory> deleteAllBy(User user) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();

        try {
            for (Answer answer : filterNotDeleted()) {
                deleteHistories.add(answer.deleteBy(user));
            }
        } catch (CannotDeleteException e) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }

        return deleteHistories;
    }

    private List<Answer> filterNotDeleted() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
