package qna.fixtures;

import qna.domain.Answers;
import qna.domain.Question;

public enum QuestionFixture {

    FIRST("첫번째 질문입니다", "첫번째 질문의 내용입니다"),
    SECOND("두번째 질문입니다", "두번째 질문의 내용입니다"),
    ;

    private final String title;
    private final String content;

    QuestionFixture(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public Question generate() {
        return generate(null);
    }

    public Question generate(final Long id) {
        return Question.builder()
                .id(id)
                .title(this.title)
                .contents(this.content)
                .answers(new Answers())
                .build();
    }
}
