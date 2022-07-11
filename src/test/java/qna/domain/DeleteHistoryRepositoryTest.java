package qna.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DeleteHistoryRepositoryTest {

	private final DeleteHistoryRepository deleteHistoryRepository;

	DeleteHistoryRepositoryTest(DeleteHistoryRepository deleteHistoryRepository) {
		this.deleteHistoryRepository = deleteHistoryRepository;
	}

	@DisplayName("DeleteHistory를 저장한다.")
	@Test
	void save() {
		DeleteHistory deleteHistory = new DeleteHistory(ContentType.QUESTION, 1L, 2L, LocalDateTime.now());
		deleteHistoryRepository.save(deleteHistory);

		DeleteHistory findHistory = deleteHistoryRepository.findById(deleteHistory.getId()).get();

		assertThat(findHistory).isEqualTo(deleteHistory);
	}
}
