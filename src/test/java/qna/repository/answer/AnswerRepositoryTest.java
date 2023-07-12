package qna.repository.answer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;
import qna.repository.AnswerRepository;
import qna.repository.config.RepositoryTestConfig;

import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("답변이 존재하지 않을 때 목록이 비어있다.")
    void findByQuestionIdAndDeletedFalse_when_answer_does_not_exist() {
        // given
        User user = new User("hyena", "1234", "헤나", "example@example.com");
        Question question = new Question("질문이 뭐에요?", "저도 모르겠어요.");
        userRepositoryFixture.save(user);
        questionRepositoryFixture.save(question);

        // when
        List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("삭제된 답변일 때 목록이 비어있다.")
    void findByQuestionIdAndDeletedFalse_when_answer_is_deleted() {
        // given
        User user = new User("hyena", "1234", "헤나", "example@example.com");
        Question question = new Question("질문이 뭐에요?", "저도 모르겠어요.");
        userRepositoryFixture.save(user);
        questionRepositoryFixture.save(question);
        Answer expected = new Answer(user, question, "내용내용");
        answerRepository.save(expected);
        expected.setDeleted(true);
        
        // when
        List<Answer> actual = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("답변의 식별자로 삭제되지 않은 답변을 조회한다.")
    void findByIdAndDeletedFalse() {
        // given
        User user = new User("hyena", "1234", "헤나", "example@example.com");
        Question question = new Question("질문이 뭐에요?", "저도 모르겠어요.");
        userRepositoryFixture.save(user);
        questionRepositoryFixture.save(question);
        Answer expected = new Answer(user, question, "내용내용");
        answerRepository.save(expected);

        // when
        Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertThat(actual).contains(expected);
    }

    @Test
    @DisplayName("답변의 식별자로 답변을 검색할 때, 삭제된 경우 검색되지 않는다.")
    void findByIdAndDeletedFalse_isNotPresent() {
        // given
        User user = new User("hyena", "1234", "헤나", "example@example.com");
        Question question = new Question("질문이 뭐에요?", "저도 모르겠어요.");
        userRepositoryFixture.save(user);
        questionRepositoryFixture.save(question);
        Answer expected = new Answer(user, question, "내용내용");
        expected.setDeleted(true);
        answerRepository.save(expected);

        // when
        Optional<Answer> actual = answerRepository.findByIdAndDeletedFalse(expected.getId());

        // then
        assertThat(actual).isNotPresent();
    }
}
