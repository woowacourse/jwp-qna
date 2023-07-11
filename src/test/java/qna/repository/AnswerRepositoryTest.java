package qna.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;
import qna.repository.config.RepositoryTestConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerRepositoryTest extends RepositoryTestConfig {

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("삭제되지 않은 답변 중에서 질문을 식별자로 조회한다.")
    void findByQuestionIdAndDeletedFalse() {
        // given
        User user = new User("hyena", "1234", "헤나", "example@example.com");
        Question question = new Question("질문이 뭐에요?", "저도 모르겠어요.");
        userRepositoryFixture.save(user);
        questionRepositoryFixture.save(question);
        Answer expected = new Answer(user, question, "내용내용");
        answerRepository.save(expected);

        // when
        List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        // then
        assertThat(actual).containsExactly(expected);
    }
}
