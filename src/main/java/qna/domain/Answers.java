package qna.domain;

import java.util.ArrayList;
import java.util.List;
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

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public void deleteAll(User user) throws CannotDeleteException {
        checkIsAllWrittenBy(user);
        answers.forEach(Answer::delete);
    }

    private void checkIsAllWrittenBy(User user) throws CannotDeleteException {
        if (!isAllDeletable(user)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private boolean isAllDeletable(User writer) {
        return answers.stream()
                .filter(it -> !it.isDeleted())
                .allMatch(it -> it.isOwner(writer));
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
