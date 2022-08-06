package qna.domain;

import qna.CannotDeleteException;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Embeddable
public class Answers {
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public Answers() {}

    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        validateOther(loginUser);
        this.answers.forEach(answer -> answer.setDeleted(true));
        return this.answers
                .stream()
                .map(answer -> new DeleteHistory(ContentType.ANSWER, answer.getId(), loginUser))
                .collect(Collectors.toList());
    }

    private void validateOther(User user) throws CannotDeleteException {
        boolean existsOtherUser = answers.stream()
                .anyMatch(answer -> !answer.isOwner(user));
        if (existsOtherUser) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    public void addAnswer(Answer answer) {
        if (!this.answers.contains(answer)) {
            answers.add(answer);
        }
    }

    public List<Answer> getAnswers() {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .collect(Collectors.toUnmodifiableList());
    }
}
