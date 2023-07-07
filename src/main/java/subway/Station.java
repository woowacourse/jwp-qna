package subway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    protected Station() {
    }

    public Station(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
