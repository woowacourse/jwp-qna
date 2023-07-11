package qna.repository.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaConfig;
import qna.repository.AnswerRepository;
import qna.repository.DeleteHistoryRepository;
import qna.repository.QuestionRepository;
import qna.repository.UserRepository;

@Import(JpaConfig.class)
@DataJpaTest
public abstract class RepositoryTestConfig {

    @Autowired
    protected UserRepository userRepositoryFixture;
    @Autowired
    protected QuestionRepository questionRepositoryFixture;
    @Autowired
    protected AnswerRepository answerRepositoryFixture;
    @Autowired
    protected DeleteHistoryRepository deleteHistoryRepositoryFixture;
}
