package qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import qna.exception.CannotDeleteException;

@Embeddable
public class Answers {

    @OneToMany(mappedBy = "question")
    private List<Answer> items = new ArrayList<>();

    public void add(Answer answer) {
        items.add(answer);
    }

    public List<DeleteHistory> delete() {
        return items.stream()
                .map(Answer::delete)
                .collect(Collectors.toList());
    }

    public void validateDeletable(User user) {
        if (hasOtherWriters(user)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private boolean hasOtherWriters(User user) {
        return items.stream()
                .anyMatch(answer -> !answer.isOwner(user));
    }

    public List<Answer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Answers{" +
                "items=" + items +
                '}';
    }
}
