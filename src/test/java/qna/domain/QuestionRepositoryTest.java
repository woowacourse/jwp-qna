package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("deleted가 false인 질문 조회")
    @Test
    void findByDeletedFalse() {
        //given
        final User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        final User sangjigi = new User("sangjigi", "password", "name", "sangjigi@slipp.net");
        userRepository.save(javajigi);
        userRepository.save(sangjigi);

        final Question question1 = questionRepository.save(new Question("title1", "contents1", javajigi));
        final Question question2 = questionRepository.save(new Question("title1", "contents1", javajigi));
        questionRepository.save(question1);
        questionRepository.save(question2);

        final List<Question> questions = questionRepository.saveAll(Arrays.asList(question1, question2));

        //when
        final List<Question> findQuestions = questionRepository.findByDeletedFalse();

        //then
        assertThat(questions).containsExactlyInAnyOrderElementsOf(findQuestions);
    }

    @DisplayName("deleted가 false인 질문을 id로 조회 - 존재 O")
    @Test
    void findByIdAndDeletedFalse() {
        //given
        final User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(javajigi);

        final Question question = questionRepository.save(new Question("title1", "contents1", javajigi));

        //when
        final Optional<Question> questionById = questionRepository.findByIdAndDeletedFalse(question.getId());

        //then
        assertThat(questionById.get()).isEqualTo(question);
    }

    @DisplayName("deleted가 false인 질문을 id로 조회 - 존재 X")
    @Test
    void findByIdAndDeletedFalseIsNotExist() {
        //given
        final User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(javajigi);

        final Question question = questionRepository.save(new Question("title1", "contents1", javajigi));
        question.setDeleted(true);

        //when & then
        assertThat(questionRepository.findByIdAndDeletedFalse(question.getId())).isEmpty();
    }
}
