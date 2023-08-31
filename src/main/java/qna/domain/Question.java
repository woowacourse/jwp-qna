package qna.domain;

import qna.exception.CannotDeleteException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @Embedded
    private final Answers answers = new Answers();

    @Column(nullable = false)
    private boolean deleted = false;

    protected Question() {
    }

    public Question(String title, User writer, String contents) {
        this(null, title, writer, contents);
    }

    public Question(Long id, String title, User writer, String contents) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public List<DeleteHistory> deleteBy(User user) {
        if (this.isNotOwner(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        List<DeleteHistory> deleteHistories = answers.deleteBy(user);
        this.deleted = true;

        deleteHistories.add(DeleteHistory.ofQuestion(id, user));
        return deleteHistories;
    }

    private boolean isNotOwner(User writer) {
        return !this.writer.equals(writer);
    }

    public Answer addAnswer(User user, Question question, String contents) {
        Answer answer = new Answer(user, question, contents);
        this.answers.add(answer);
        return answer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public User getWriter() {
        return writer;
    }

}
