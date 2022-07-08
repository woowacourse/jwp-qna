package study.subway.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "line")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 외래키의 관리자는 line_id FK가 있는 Station이므로 주인이 아닌 line에서는 연관관계를 통한 조회만 가능!
    // Station에 Line 정보를 등록하는 것만 DB에 영향 미침! stations 필드의 변화가 테이블에 반영되지 않음!
    @OneToMany(mappedBy = "line") // mappedBy를 통해 FK가 관리되  필드명 명시
    private List<Station> stations = new ArrayList<>(); // NPE 방지! 생성자에서 받도록 하는 경우, JPA는 해당 생성자가 사용되지 않을 수 있음

    // 프록시, 리플렉션을 위해 noArgsConstructor가 필요함
    protected Line(){}

    public Line(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    // 연관관계 편의 메서드
    public void addStation(Station station) {
        stations.add(station); // 연관관계의 주인이 아니므로 테이블에 영향X
        station.setLine(this); // 실제로 station에 변화를 추가해줘야 테이블에 반영O
    } // 무한 루프 주의!
}
