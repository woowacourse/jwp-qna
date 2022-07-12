package qna.domain;

import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class User extends MappedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(length = 50)
    private String email;

    @OneToMany(mappedBy = "deletedBy")
    private final List<DeleteHistory> deleteHistories = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private final List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    private final List<Question> questions = new ArrayList<>();

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this(null, userId, password, name, email);
    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User loginUser, User target) {
        if (!matchUserId(loginUser.userId)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.password)) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
        this.updatedAt = LocalDateTime.now();
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(User target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return name.equals(target.name) &&
                email.equals(target.email);
    }

    public void addAnswer(Answer answer) {
        if (!this.answers.contains(answer)) {
            answers.add(answer);
        }
    }

    public boolean isGuestUser() {
        return false;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addDeleteHistory(DeleteHistory deleteHistory) {
        if (!this.deleteHistories.contains(deleteHistory)) {
            deleteHistories.add(deleteHistory);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void addQuestion(Question question) {
        if (!this.questions.contains(question)) {
            this.questions.add(question);
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
