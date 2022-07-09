package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    private DeleteHistoryRepository deleteHistories;

    @Autowired
    private AnswerRepository answers;

    @Autowired
    private QuestionRepository questions;

    @Autowired
    private UserRepository users;

    @BeforeEach
    void setUp() {
        users.deleteAll();
        questions.deleteAll();
        answers.deleteAll();
        deleteHistories.deleteAll();
    }

    @DisplayName("저장 기록을 저장한다.")
    @Test
    void save() {
        Question question = questions.save(new Question("title", "questionContents"));
        User user = users.save(new User("알린", "12345678", "장원영", "ozragwort@gmail.com"));
        DeleteHistory deleteHistory = new DeleteHistory(
                ContentType.QUESTION,
                question.getId(),
                user.getId(),
                question.getCreatedAt()
        );

        DeleteHistory actual = deleteHistories.save(deleteHistory);

        assertThat(actual == deleteHistory).isTrue();
    }

}
