package qna.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@Import(JpaAuditingConfig.class)
@DataJpaTest
public class RepositoryTest {
    @Autowired
    protected AnswerRepository answerRepository;

    @Autowired
    protected DeleteHistoryRepository deleteHistoryRepository;

    @Autowired
    protected QuestionRepository questionRepository;

    @Autowired
    protected UserRepository userRepository;
}
