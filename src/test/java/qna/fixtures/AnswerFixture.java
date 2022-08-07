package qna.fixtures;

import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;

public enum AnswerFixture {

    FIRST("첫번째 답변의 내용입니다"),
    SECOND("두번째 답변의 내용입니다"),
    ;

    private final String contents;

    AnswerFixture(final String contents) {
        this.contents = contents;
    }

    public Answer generate(final User writer, final Question question) {
        return generate(null, writer, question);
    }

    public Answer generate(final Long id, final User writer, final Question question) {
        return Answer.builder()
                .id(id)
                .contents(contents)
                .writer(writer)
                .question(question)
                .build();
    }
}
