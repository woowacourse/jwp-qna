package qna.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class DeletedHistoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void checkCreatedTime() {
        User user = userRepository.save(new User("alpha", "asd", "alpha", "alpha@slipp.net"));
        DeleteHistory deleteHistory = deleteHistoryRepository.save(new DeleteHistory(ContentType.ANSWER, 1L, user));

        assertAll(
                () -> assertThat(deleteHistory.getDeletedById()).isEqualTo(user.getId()),
                () -> assertThat(deleteHistory.getCreateDate()).isNotNull()
        );
    }
}
