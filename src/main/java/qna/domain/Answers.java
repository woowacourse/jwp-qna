package qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

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

    public boolean isAllDeletable(User user) {
        return answers.stream()
                .filter(it -> !it.isDeleted())
                .allMatch(it -> it.isOwner(user));
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
