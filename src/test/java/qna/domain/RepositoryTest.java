package qna.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    protected UserRepository users;

    @Autowired
    protected QuestionRepository questions;

    @Autowired
    protected AnswerRepository answers;

    @Autowired
    protected DeleteHistoryRepository deleteHistories;

    protected User saveUser() {
        return users.save((new User("test_user", "test_password", "사용자1", "user@gmail.com")));
    }

    protected Question saveQuestion(User user) {
        return questions.save(new Question("질문1입니다.", "질문1의 내용입니다.").writeBy(user));
    }
}
