package qna.domain;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CashManager {

    @Autowired
    private EntityManager entityManager;

    protected void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
