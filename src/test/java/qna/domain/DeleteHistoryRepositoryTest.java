package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaAuditingConfig;


@DataJpaTest
@Import(JpaAuditingConfig.class)
class DeleteHistoryRepositoryTest {

    private DeleteHistory HISTORY = new DeleteHistory(ContentType.QUESTION, 1L, 1L);

    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void 삭제이력을_저장한다() {
        // when
        DeleteHistory actual = deleteHistoryRepository.save(HISTORY);

        // then
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 삭제이력을_조회한다() {
        // given
        DeleteHistory actual = deleteHistoryRepository.save(HISTORY);

        // when
        DeleteHistory expected = deleteHistoryRepository.findById(actual.getId()).get();

        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
