package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.QuestionTest.Q1;
import static qna.domain.QuestionTest.Q2;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에 같은 객체가 나오게 된다.")
    @Test
    void findByDeletedFalse() {
        Question savedQ1 = questionRepository.save(Q1);
        Question savedQ2 = questionRepository.save(Q2);
        List<Question> questions = questionRepository.findByDeletedFalse();

        assertThat(questions).containsExactly(savedQ1, savedQ2);
    }

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에, 조건이 id뿐이라면 select 쿼리를 날리지 않는다.")
    @Test
    void findById() {
        Question savedQ1 = questionRepository.save(Q1);

        Optional<Question> question = questionRepository.findById(savedQ1.getId());

        assertThat(question.get()).isNotNull();
    }

    @DisplayName("객체를 찾아올 때는 한 영속성 컨텍스트안에서 id 값으로 매핑되어있기 때문에, 조건이 id뿐이 아니라면, select 쿼리를 날린다.")
    @Test
    void findByIdAndDeletedFalse() {
        Question savedQ1 = questionRepository.save(Q1);

        Optional<Question> question = questionRepository.findByIdAndDeletedFalse(savedQ1.getId());

        assertThat(question.get()).isNotNull();
    }

    @DisplayName("객체를 찾아올 때, 쓰기 지연 로딩에 걸려있는 쿼리가 있는 상태라면, 먼저 flush를 해버리고, 그 다음 select 쿼리를 날린다.")
    @Test
    void findByIdAndDeletedFalseReturnOptionalEmpty() {
        Question savedQ1 = questionRepository.save(Q1);
        savedQ1.setDeleted(true);

        Optional<Question> question = questionRepository.findByIdAndDeletedFalse(savedQ1.getId());

        assertThat(question).isEmpty();
    }
}
