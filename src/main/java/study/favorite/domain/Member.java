package study.favorite.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // member 테이블에서는 복수의 favorite 로우에 대한 연관관계를 FK로 관리할 수 없음!
    // 디폴트: JPA는 자동으로 member_favorites 연결 테이블을 만들어 FK를 관리하게 됨!
    @JoinColumn(name = "member_id") // 상대방 테이블에 member_id FK를 관리하도록 명시
    @OneToMany
    private List<Favorite> favorites = new ArrayList<>();
}

// OneToMany & OneToOne : 상대방쪽에서 FK를 관리한다!
// ManyToOne : 자신이 FK를 관리한다!

// 일대다 단방향이 필요한 경우도 있음!
// 그래도 UPDATE 호출 등 성능상의 단점으로 인해 다대일 양방향 매핑을 권장함!
