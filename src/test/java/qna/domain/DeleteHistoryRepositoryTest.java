package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {
    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;

    @Test
    void 컨텐츠_타입이_일치하는_삭제_기록_조회() {
        // given
        final DeleteHistory questionDeletedHistory
                = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now());
        final DeleteHistory answerDeletedHistory
                = new DeleteHistory(ContentType.ANSWER, 2L, 2L, LocalDateTime.now());
        deleteHistoryRepository.saveAll(List.of(questionDeletedHistory, answerDeletedHistory));

        // when
        final List<DeleteHistory> questionDeleteHistories
                = deleteHistoryRepository.findDeleteHistoriesByContentType(ContentType.QUESTION);

        // then
        assertThat(questionDeleteHistories).containsExactly(questionDeletedHistory);
    }

    @Test
    void 특정_날짜_구간에_해당하는_삭제_기록_조회() {
        // given
        final DeleteHistory pastWeekDeleteHistory
                = new DeleteHistory(ContentType.QUESTION, 1L, 1L, LocalDateTime.now().minusDays(7));
        final DeleteHistory todayDeleteHistory
                = new DeleteHistory(ContentType.QUESTION, 2L, 2L, LocalDateTime.now());
        deleteHistoryRepository.saveAll(List.of(pastWeekDeleteHistory, todayDeleteHistory));

        // when
        final List<DeleteHistory> todayDeleteHistories
                = deleteHistoryRepository
                .findDeleteHistoriesByCreateDateBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        // then
        assertThat(todayDeleteHistories).containsExactly(todayDeleteHistory);
    }
}
