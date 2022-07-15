package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import qna.config.JPAConfigurer;

@DataJpaTest
@Import(JPAConfigurer.class)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("deleted가 false인 답변들을 질문 id로 조회")
    @Test
    void findByQuestionIdAndDeletedFalse() {
        //given
        final User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(javajigi);

        final Question question = questionRepository.save(new Question("title1", "contents1", javajigi));

        final List<Answer> answers = answerRepository.saveAll(
                Arrays.asList(new Answer(javajigi, question, "Answers Contents1"))
        );

        //when
        final List<Answer> getAnswers = answerRepository.findByQuestionIdAndDeletedFalse(question.getId());

        //then
        assertThat(answers).containsExactlyInAnyOrderElementsOf(getAnswers);
    }

    @DisplayName("deleted가 false인 답변들을 답변 id로 조회 - 존재 O")
    @Test
    void findByIdAndDeletedFalse() {
        //given
        userRepository.save(UserTest.JAVAJIGI);
        questionRepository.save(QuestionTest.Q1);

        //when
        final Answer answer = answerRepository.save(AnswerTest.A1);

        //then
        assertThat(answerRepository.findByIdAndDeletedFalse(answer.getId())).contains(answer);
    }

    @DisplayName("deleted가 false인 답변들을 답변 id로 조회 - 존재 X")
    @Test
    void findByIdAndDeletedFalseIsNotExist() {
        //given
        final User javajigi = new User("javajigi", "password", "name", "javajigi@slipp.net");
        userRepository.save(javajigi);

        final Question question = questionRepository.save(new Question("title1", "contents1", javajigi));

        final Answer answer = answerRepository.save(new Answer(javajigi, question, "content1"));
        answer.setDeleted(true);

        //when & then
        assertThat(answerRepository.findByIdAndDeletedFalse(answer.getId())).isEmpty();
    }
}
