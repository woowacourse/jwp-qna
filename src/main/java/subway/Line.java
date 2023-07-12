package subway;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.REMOVE) //연관관계 주인의 필드 이름?? 불핑요한 테이블이 만들어지는걸 방지할 수도 있다.
    private List<Station> stations = new ArrayList<>();

    public Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        station.setLine(this);
        stations.add(station);
    }
}
