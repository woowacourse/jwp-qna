package qna.fixture;

import qna.domain.Question;
import qna.repository.QuestionRepository;

public class QuestionRepositoryFixture {

    private final QuestionRepository questionRepository;

    public QuestionRepositoryFixture(final QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question save(final String title, final String contents) {
        return questionRepository.save(new Question(title, contents));
    }
}
