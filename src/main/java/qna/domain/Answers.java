package qna.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Answers {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<Answer> answers;

    public Answers() {
        answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean hasOthers(User writer) {
        return answers.stream()
                .anyMatch(answer -> !answer.isOwner(writer));
    }

    public void delete() {
        for (Answer answer : answers) {
            answer.delete();
        }
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
