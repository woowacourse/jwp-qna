package study.bidirectional;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "bi_line")
@Entity
public class BiDirectionalLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "biLine") // mappedBy = 연관 관계 주인의 필드 이름
    private List<BiDirectionalStation> stations = new ArrayList<>();

    public BiDirectionalLine(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BiDirectionalLine(String name) {
        this.name = name;
    }

    public BiDirectionalLine() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BiDirectionalStation> getStations() {
        return stations;
    }

    public void addStation(BiDirectionalStation station) {
        stations.add(station);
        station.setLine(this);
    }
}
