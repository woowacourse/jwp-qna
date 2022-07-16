package study.bidirectional;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "bi_station")
@Entity
public class BiDirectionalStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private BiDirectionalLine biLine;

    public BiDirectionalStation(Long id, String name, BiDirectionalLine biLine) {
        this.id = id;
        this.name = name;
        this.biLine = biLine;
    }

    public BiDirectionalStation(String name) {
        this.name = name;
    }

    public BiDirectionalStation() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BiDirectionalLine getBiLine() {
        return biLine;
    }

    public void setLine(BiDirectionalLine line) {
        // 지하철에 연결된 line을 다른 노선으로 수정하고자 하는 경우,
        // 두 가지 노선 모두에 같은 지하철역이 등록되는 문제 발생 가능.
        // 현재 등록된 line에서 자신(station)을 제거시킨 후 대체함으로써 예방 가능.
        if (Objects.nonNull(this.biLine)) {
            this.biLine.getStations().remove(this);
        }
        this.biLine = line;
        line.getStations().add(this); // 주의: addStation() 호출시 순환 참조!
    }
}
