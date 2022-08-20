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

    public List<DeleteHistory> delete(User loginUser) {
        this.answers.forEach(answer -> answer.unActivate(loginUser));
        return this.answers
                .stream()
                .map(answer -> new DeleteHistory(ContentType.ANSWER, answer.getId(), loginUser))
                .collect(Collectors.toList());
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
