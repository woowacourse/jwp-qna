package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DeleteHistoryRepositoryTest {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final DeleteHistoryRepository deleteHistoryRepository;
    private DeleteHistory DELETE_HISTORY;

    public DeleteHistoryRepositoryTest(final UserRepository userRepository,
                                       final QuestionRepository questionRepository,
                                       final DeleteHistoryRepository deleteHistoryRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.deleteHistoryRepository = deleteHistoryRepository;
    }

    @BeforeEach
    void setUp() {
        final User writer = userRepository.save(UserFixture.SANJIGI);
        final Question question = questionRepository.save(new Question("title1", "contents1").writeBy(writer));
        DELETE_HISTORY = new DeleteHistory(ContentType.QUESTION, question.getId(), writer);
    }

    @DisplayName("삭제내역을 저장한다.")
    @Test
    void save() {
        // given
        // when
        final DeleteHistory saved = deleteHistoryRepository.save(DELETE_HISTORY);

        // then
        assertThat(deleteHistoryRepository.findById(saved.getId()).get())
                .usingRecursiveComparison()
                .isEqualTo(saved);
    }

    @DisplayName("식별자로 조회한 질문은 서로 동일하다.")
    @Test
    void identity() {
        // given
        // when
        final DeleteHistory saved = deleteHistoryRepository.save(DELETE_HISTORY);
        final DeleteHistory actual = deleteHistoryRepository.findById(saved.getId()).get();

        // then
        assertThat(saved).isSameAs(actual);
    }
}


