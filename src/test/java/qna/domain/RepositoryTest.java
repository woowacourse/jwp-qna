package qna.domain;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import qna.config.JpaConfig;

@Import(JpaConfig.class)
@DataJpaTest
@TestConstructor(autowireMode = AutowireMode.ALL)
public class RepositoryTest {

}
