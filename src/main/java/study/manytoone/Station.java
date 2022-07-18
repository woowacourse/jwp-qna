package study.manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 디폴트는 AUTO지만 Spring, 하이버네이트 등에 따라 달라지므로 DB에 PK 생성 전략 위임하는 Identity 전략 사용
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    public Station(Long id, String name, Line line) {
        this.id = id;
        this.name = name;
        this.line = line;
    }

    public Station(Long id, String name) {
        this(id, name, null);
    }

    public Station(String name) {
        this.name = name;
    }

    public Station() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
