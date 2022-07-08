package subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * create table station (
 *     id bigint generated by default as identity,
 *     name varchar(255) not null,
 *     primary key (id)
 * )
 */

@Entity
public class Station  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 디폴트는 AUTO지만 Spring, 하이버네이트 등에 따라 달라지므로 Identity 전략으로 지정하는게 정신 건강에 이로움.
    private Long id;
    // save 호출시, isNew()가 참이면 persist() / 거짓인 경우 병합(merge) 실행
    // 디폴트: null 혹은 0L인 경우, 새로운 엔티티로 간주하여 DB에서 1만큼 증가시킨 식별자를 사용. / 그 외의 값인 경우, 병합(merge)

    @Column(nullable = false)
    private String name;

    @ManyToOne // optional = false : 필요 없어도 일단 eager load
    @JoinColumn(name = "line_id") // 디폴트가 `필드_PK`이므로 불필요함
    private Line line;

    protected Station(){}

    public Station(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Station{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setLine(Line line) {
        this.line = line;
        // 무한 루프 방어 로직
        if (line != null && !line.getStations().contains(this)) {
            line.addStation(this);
        }
    }

    public Line getLine() {
        return line;
    }
}

// SELECT문 없애기
// public class Station implements Persistable<Long> {
//
//    @Override
//    public boolean isNew() {
//        return true;
//    }
//}
