package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QuestionRepositoryTest extends RepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void Question을_저장한다() {
        final User user = UserFixture.fixture();

        final Question question = questionRepository.save(new Question("질문입니다", "질문이에요", user));
        Question found = questionRepository.findById(question.getId()).get();

        assertThat(question).isSameAs(found);
    }
}
