package qna.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Answers {

    private final List<Answer> answers;

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<DeleteHistory> deleteBy(User user){
        return answers.stream()
                .map(answer -> answer.deleteBy(user))
                .collect(Collectors.toList());
    }
}
