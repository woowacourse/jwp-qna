package subway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // (1)
@Table(name = "station") // (2)
public class Station {


    @Id // (3)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4)
    private Long id;

    @Column(name = "name", unique = true, nullable = false) // (5)
    private String name;

    protected Station() { // (6)
    }

    public Station(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void updatName(final String name) {
        this.name = name;
    }
}
