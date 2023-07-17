package qna.repository.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaConfig;
import qna.fixture.AnswerRepositoryFixture;
import qna.fixture.DeleteHistoryRepositoryFixture;
import qna.fixture.QuestionRepositoryFixture;
import qna.fixture.UserRepositoryFixture;
import qna.repository.AnswerRepository;
import qna.repository.DeleteHistoryRepository;
import qna.repository.QuestionRepository;
import qna.repository.UserRepository;

@Import(JpaConfig.class)
@DataJpaTest
public abstract class RepositoryTestConfig {

    protected UserRepositoryFixture userRepositoryFixture;
    protected QuestionRepositoryFixture questionRepositoryFixture;
    protected AnswerRepositoryFixture answerRepositoryFixture;
    protected DeleteHistoryRepositoryFixture deleteHistoryRepositoryFixture;

    @BeforeEach
    void setUserRepositoryFixture(
            @Autowired final UserRepository userRepository,
            @Autowired final QuestionRepository questionRepository,
            @Autowired final AnswerRepository answerRepository,
            @Autowired final DeleteHistoryRepository deleteHistoryRepository
    ) {
        userRepositoryFixture = new UserRepositoryFixture(userRepository);
        questionRepositoryFixture = new QuestionRepositoryFixture(questionRepository);
        answerRepositoryFixture = new AnswerRepositoryFixture(answerRepository);
        deleteHistoryRepositoryFixture = new DeleteHistoryRepositoryFixture(deleteHistoryRepository);
    }
}
