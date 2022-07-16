package study.manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NoGeneratedIdStation {

    @Id // @GeneratedValue(strategy = GenerationType.IDENTITY) // 직접 PK 지정하여 저장
    private Long id;

    @Column(nullable = false)
    private String name;

    public NoGeneratedIdStation(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public NoGeneratedIdStation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
