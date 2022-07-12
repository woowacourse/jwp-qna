package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
