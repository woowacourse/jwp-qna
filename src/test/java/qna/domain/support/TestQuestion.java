package qna.domain.support;

import qna.domain.Question;

public class TestQuestion {

    private TestQuestion() {

    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {

        private Long id;
        private String title;
        private String contents;
        private Long writerId;

        public QuestionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuestionBuilder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public QuestionBuilder writerId(Long writerId) {
            this.writerId = writerId;
            return this;
        }

        public Question build() {
            return new Question(id, title, contents, writerId);
        }
    }

}
