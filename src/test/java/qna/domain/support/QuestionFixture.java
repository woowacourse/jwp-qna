package qna.domain.support;

import qna.domain.Question;

public class QuestionFixture {

    private static final String TITLE = "title";
    private static final String CONTENTS = "contents";

    public static Question createQuestion(Long writerId) {
        return create(null, TITLE, CONTENTS, writerId);
    }

    public static Question createQuestion(String title, String contents, Long writerId) {
        return create(null, title, contents, writerId);
    }

    private static Question create(Long id, String title, String contents, Long writerId) {
        return TestQuestion.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .writerId(writerId)
                .build();
    }
}
