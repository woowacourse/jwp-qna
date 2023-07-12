package qna.fixture;

import qna.repository.AnswerRepository;

public class AnswerRepositoryFixture {

    private final AnswerRepository answerRepository;

    public AnswerRepositoryFixture(final AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }
}
