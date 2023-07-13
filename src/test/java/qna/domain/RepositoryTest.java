package qna.domain;

import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;

@Import(JpaAuditingConfig.class)
@DataJpaTest
abstract class RepositoryTest {

    @Autowired
    protected EntityManager em;
}
