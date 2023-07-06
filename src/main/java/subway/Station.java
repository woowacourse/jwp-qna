package subway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity // (1)
@Table(name = "station") // (2)
public class Station {
    @Id // (3)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4)
    private Long id;

    @Column(name = "name", nullable = false) // (5)
    private String name;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;
    
    protected Station() { // (6)
    }

    public Station(String name) {
        this.name = name;
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

    public void setLine(Line line){
        this.line = line;
        List<Station> stations = line.getStations();
        if(!stations.contains(this)){
            stations.add(this);
        }
//        line.getStations().add(this);
    }

    public Line getLine() {
        return line;
    }
}
