package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.UserTest.JAVAJIGI;
import static qna.domain.UserTest.SANJIGI;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AnswersTest {

    @Test
    void 답변들에_다른_유저의_답변이_포함되지_않을_경우_거짓() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, QuestionTest.Q1(), "contents"));
        answers.add(new Answer(JAVAJIGI, QuestionTest.Q1(), "contents2"));

        // when & then
        assertThat(answers.hasOthers(JAVAJIGI)).isFalse();
    }

    @Test
    void 답변들에_다른_유저의_답변이_포함될_경우_참() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, QuestionTest.Q1(), "contents"));
        answers.add(new Answer(SANJIGI, QuestionTest.Q1(), "contents2"));

        // when & then
        assertThat(answers.hasOthers(JAVAJIGI)).isTrue();
    }

    @Test
    void 답변들을_삭제한다() {
        // given
        Answers answers = new Answers();
        answers.add(new Answer(JAVAJIGI, QuestionTest.Q1(), "contents"));
        answers.add(new Answer(SANJIGI, QuestionTest.Q1(), "contents2"));

        // when
        answers.delete();

        // then
        assertThat(answers.getAnswers()).allMatch(Answer::isDeleted);
    }
}
