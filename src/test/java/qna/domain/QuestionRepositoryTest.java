package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import qna.support.RepositoryTest;

@RepositoryTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("수정이 일어났을 때 updatedAt이 잘 생성되는지 확인한다.")
    @Test
    void saveUpdatedAt() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questionRepository.save(question);

        LocalDateTime updatedAt = savedQuestion.getUpdatedAt();
        savedQuestion.setDeleted(true);
        questionRepository.flush();

        Question updatedQ1 = questionRepository.findById(savedQuestion.getId()).get();

        assertThat(updatedQ1.getUpdatedAt()).isAfter(updatedAt);
    }

    @DisplayName("저장할 때 연관관계인 User가 영속성 컨텍스트에 없다면, 예외가 발생한다.")
    @Test
    void saveWithUserNotInPersistence() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        Question question = new Question("title1", "contents1").writeBy(tiki);
        questionRepository.save(question);

        assertThatThrownBy(() -> questionRepository.flush())
                .isExactlyInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @DisplayName("연관관계 주인인 Question에서 Writer를 변경만하더라도, 더티체킹으로 update 쿼리를 날려 변경사항이 저장된다.")
    @Test
    void updateWriterInQuestion() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User tiki2 = new User("tiki2", "password", "티키2", "yh20studio@naver.com");
        User savedUser1 = userRepository.save(tiki);
        User savedUser2 = userRepository.save(tiki2);
        Question question = new Question("title1", "contents1").writeBy(savedUser1);
        Question savedQuestion = questionRepository.save(question);

        savedQuestion.writeBy(savedUser2);
        Question updatedQuestion = questionRepository.findById(savedQuestion.getId()).get();

        assertThat(updatedQuestion.getWriter()).isEqualTo(savedUser2);
    }

    @DisplayName("연관관계 주인인 Question에서 Writer를 변경할때, 실제 순수 객체와의 상태를 맞추기 위해서 편의 메서드를 이용했다.")
    @Test
    void updateWriterInQuestionWithConvenienceMethod() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User tiki2 = new User("tiki2", "password", "티키2", "yh20studio@naver.com");
        User savedUser1 = userRepository.save(tiki);
        User savedUser2 = userRepository.save(tiki2);
        Question question = new Question("title1", "contents1").writeBy(savedUser1);
        Question savedQuestion = questionRepository.save(question);

        savedQuestion.writeBy(savedUser2);

        assertThat(savedUser2.getQuestions()).containsExactly(savedQuestion);
    }

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에 같은 객체가 나오게 된다.")
    @Test
    void findByDeletedFalse() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question question1 = new Question("title1", "contents1").writeBy(savedUser);
        Question question2 = new Question("title2", "contents2").writeBy(savedUser);
        Question savedQuestion1 = questionRepository.save(question1);
        Question savedQuestion2 = questionRepository.save(question2);
        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).containsExactly(savedQuestion1, savedQuestion2);
    }

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에, 조건이 id뿐이라면 select 쿼리를 날리지 않는다.")
    @Test
    void findById() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questionRepository.save(question);

        Optional<Question> foundQuestion = questionRepository.findById(savedQuestion.getId());

        assertThat(foundQuestion.get()).isNotNull();
    }

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에, 조건이 id뿐이 아니라면, select 쿼리를 날린다.")
    @Test
    void findByIdAndDeletedFalse() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questionRepository.save(question);

        Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());

        assertThat(foundQuestion.get()).isNotNull();
    }

    @DisplayName("객체를 찾아올 때, 쓰기 지연 로딩에 걸려있는 쿼리가 있는 상태라면, 먼저 flush를 해버리고, 그 다음 select 쿼리를 날린다.")
    @Test
    void findByIdAndDeletedFalseReturnOptionalEmpty() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);
        Question question = new Question("title1", "contents1").writeBy(savedUser);
        Question savedQuestion = questionRepository.save(question);
        savedQuestion.setDeleted(true);

        Optional<Question> foundQuestion = questionRepository.findByIdAndDeletedFalse(savedQuestion.getId());

        assertThat(foundQuestion).isEmpty();
    }
}
