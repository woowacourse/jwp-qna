package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import qna.support.RepositoryTest;

@RepositoryTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("tiki와 같이 객체 안에 id 값이 존재한다면, 해당 id를 가진 유저가 있는지 먼저 select문을 보내고, id 값을 null로 만들고 insert 쿼리를 날린다.")
    @Test
    void save() {
        User tiki = new User(1L, "tiki", "password", "name", "tiki@naver.com");
        User savedUser = userRepository.save(tiki);

        assertThat(savedUser).isNotSameAs(tiki);
    }

    @DisplayName("userId 값으로 데이터를 찾기 때문에 select 쿼리를 보낸다.")
    @Test
    void findByUserId() {
        User user = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(user);

        Optional<User> byUserId = userRepository.findByUserId(savedUser.getUserId());

        assertThat(byUserId.get()).isSameAs(savedUser);
    }

    @DisplayName("연관관계 주인아닌 User에서 Question을 변경하더라도, 해당 내용은 쿼리로 날라가지 않는다.")
    @Test
    void updateQuestionInUser() {
        User tiki = new User("tiki", "password", "티키", "yh20studio@gmail.com");
        User savedUser = userRepository.save(tiki);

        Question question1 = new Question("title1", "contents1").writeBy(savedUser);
        Question question2 = new Question("title2", "contents2");
        Question savedQuestion1 = questionRepository.save(question1);

        User foundUser = userRepository.findById(savedUser.getId()).get();
        foundUser.getQuestions().add(question2);
        userRepository.flush();

        assertThat(question2.getWriter()).isNull();
    }
}
