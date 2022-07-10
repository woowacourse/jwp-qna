package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static qna.domain.QuestionTest.Q1;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JpaConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaConfig.class)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("저장할 때 createdAt이 잘 생성되는지 검증한다.")
    @Test
    void saveCreatedAt() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question savedQuestion = questionRepository.save(Q1);
        Answer expected = new Answer(savedUser, savedQuestion, "Answers Contents1");
        Answer actual = answerRepository.save(expected);

        assertThat(actual.getCreatedAt()).isNotNull();
    }

    @DisplayName("저장하고 리턴된 객체는 저장하기전의 객체와 참조 값이 같은 객체이다.")
    @Test
    void save() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question savedQuestion = questionRepository.save(Q1);
        Answer expected = new Answer(savedUser, savedQuestion, "Answers Contents1");
        Answer actual = answerRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @DisplayName("한 질문에 대해서 답변을 3개를 저장하고, 한개를 삭제했다고 상태를 변경하면 총 2개의 답변이 찾아진다.")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question savedQuestion = questionRepository.save(Q1);
        Answer answer1 = answerRepository.save(new Answer(savedUser, savedQuestion, "Answers Contents1"));
        Answer answer2 = answerRepository.save(new Answer(savedUser, savedQuestion, "Answers Contents2"));
        Answer answer3 = answerRepository.save(new Answer(savedUser, savedQuestion, "Answers Contents3"));
        answer3.setDeleted(true);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(
                savedQuestion.getId());

        assertThat(answers).containsExactly(answer1, answer2);
    }

    @DisplayName("특정 아이디를 가진 질문을 찾는다면, 리턴된 엔티티는 이전에 저장했던 엔티티와 같다.")
    @Test
    void findByIdAndDeletedFalse() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question savedQuestion = questionRepository.save(Q1);
        Answer answer = answerRepository.save(new Answer(savedUser, savedQuestion, "Answers Contents1"));

        Optional<Answer> foundAnswer = answerRepository.findByIdAndDeletedFalse(answer.getId());

        assertThat(foundAnswer.get()).isEqualTo(answer);
    }
}
