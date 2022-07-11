package qna.domain;

public class QuestionFixtures {

    public static Question createQuestion(final String title, final String contents, final User user) {
        return new Question(title, contents).writeBy(user);
    }
}
