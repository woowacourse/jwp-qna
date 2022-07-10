package study;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Station2 {

    @Id // @GeneratedValue(strategy = GenerationType.IDENTITY) // 직접 PK 지정하여 저장
    private Long id;

    @Column(nullable = false)
    private String name;

    public Station2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station2() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
