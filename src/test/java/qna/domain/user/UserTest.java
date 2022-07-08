package qna.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {

    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    private UserRepository users;

    @Autowired
    private EntityManager em;

    /**
     * save 메서드 호출시, isNew()를 통해 persist() 혹은 merge() 호출하여 영속 상태로 변경. (1) 엔티티의 id 값이 0L 혹은 null인 경우, persist() 호출하여 저장.
     * (2) 엔티티의 id 값이 그 외의 값인 경우, merge() 호출하여 비영속 병합 실행. - 주의: 병합은 준영속/비영속 여부를 판단하지 않으며, 그저 식별자 값을 기준으로 엔티티를 조회할 수 있으면
     * 불러서 병합하고, 조회할 수 없으면 새로 생성하여 병합. - 주의: merge()의 매개변수로 들어오는 엔티티는 그 반환값인 영속 상태의 엔티티와 완전히 별개로 간주됨.
     * <p>
     * cf) IDENTITY 전략 설정시 기본 키 생성 값을 DB에 위임.
     */
    @DisplayName("save() 메서드는 isNew() 메서드를 통해 엔티티의 식별자 값에 따라 persist() 혹은 merge() 호츨하여 엔티티를 영속 상태로 변경")
    @Nested
    class SaveTest {

        @Test
        void save_호출시_DB에서_생성한_식별자_값이_id로_포함된_영속_상태의_엔티티_반환() {
            final User 비영속_엔티티 = new User(null, "javajigi", "password", "name", "javajigi@slipp.net");

            final User 영속_엔티티 = users.save(비영속_엔티티);

            assertThat(영속_엔티티.getId()).isNotNull();
        }

        @Test
        void 식별자_값이_0_혹은_null인_엔티티는_DB에서_생성한_식별자_값이_기존_엔티티에도_설정됨() {
            final User 비영속_엔티티 = new User(null, "javajigi", "password", "name", "javajigi@slipp.net");
            assertThat(비영속_엔티티.getId()).isNull();

            final User 영속_엔티티 = users.save(비영속_엔티티);

            assertThat(비영속_엔티티.getId()).isNotNull();
            assertThat(영속_엔티티).isEqualTo(비영속_엔티티);
        }

        @Test
        void 유효하지만_DB에_저장되지_않은_식별자의_엔티티_저장시_merge가_호출되면서_매개변수의_엔티티와는_별개의_신규_엔티티_저장_후_반환() {
            final User 비영속_엔티티 = new User(100L, "javajigi", "password", "name", "javajigi@slipp.net");

            final User 영속_엔티티 = users.save(비영속_엔티티);

            assertThat(비영속_엔티티.getId()).isEqualTo(100L);
            assertThat(영속_엔티티.getId()).isNotEqualTo(100L);
            assertThat(영속_엔티티).isNotEqualTo(비영속_엔티티);
        }

        @Test
        void 영속성_컨텍스트에_이미_존재하는_식별자로_새로운_엔티티_저장시_merge가_호출되면서_값_업데이트() {
            final User 비영속_엔티티 = new User("javajigi", "password", "name", "javajigi@slipp.net");
            final User 영속_엔티티1 = users.save(비영속_엔티티);
            final Long 식별자 = 영속_엔티티1.getId();
            em.flush(); // INSERT

            final User 동일_식별자의_비영속_엔티티 = new User(식별자, "javajigi", "password2", "name2", "javajigi2@slipp.net");
            final User 영속_엔티티2 = users.save(동일_식별자의_비영속_엔티티);
            em.flush(); // UPDATE

            assertThat(영속_엔티티1).usingRecursiveComparison().isEqualTo(영속_엔티티2);
            assertThat(동일_식별자의_비영속_엔티티).usingRecursiveComparison().isEqualTo(영속_엔티티2);
        }
    }
}
