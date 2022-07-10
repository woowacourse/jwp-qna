package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

	@Autowired
	private DeleteHistoryRepository deleteHistoryRepository;

	@DisplayName("DeleteHistory를 저장한다.")
	@Test
	void save() {
		DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, 2L, LocalDateTime.now());
		deleteHistoryRepository.save(deleteHistory);

		DeleteHistory findHistory = deleteHistoryRepository.findById(deleteHistory.getId()).get();

		assertThat(findHistory).isEqualTo(deleteHistory);
	}
}
