package qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import qna.CannotDeleteException;

@Embeddable
@Access(AccessType.FIELD)
public class Answers {

    @OneToMany(mappedBy = "question")
    private List<Answer> values = new ArrayList<>();

    public Answers() {
    }

    public List<Answer> getValues() {
        return values;
    }

    public void add(Answer answer) {
        values.add(answer);
    }

    public void validateAnswerNotExists(User loginUser) {
        for (Answer answer : values) {
            if (!answer.isOwner(loginUser)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }
    }

    public DeleteHistories deleteSoft(User loginUser) {
        DeleteHistories deleteHistories = new DeleteHistories();
        for (Answer answer : values) {
            DeleteHistory deleteHistory = answer.deleteSoft(loginUser);
            deleteHistories.add(deleteHistory);
        }
        return deleteHistories;
    }
}
