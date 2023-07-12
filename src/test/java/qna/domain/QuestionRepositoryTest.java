package qna.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends RepositoryTest{

    @Test
    void Question을_저장한다() {
        final User user = userRepository.save(UserTest.JAVAJIGI);
        final Question question = questionRepository.save(new Question("질문입니다", "질문이에요", user));
        Question found = questionRepository.findById(question.getId()).get();

        assertThat(question).isSameAs(found);
    }
}
