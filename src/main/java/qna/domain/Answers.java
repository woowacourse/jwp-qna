package qna.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<DeleteHistory> deleteAllBy(User user) {
        return answers.stream()
                .filter(answer -> !answer.isDeleted())
                .map(answer -> answer.deleteBy(user))
                .collect(Collectors.toList());
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
