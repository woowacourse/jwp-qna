# jwp-qna

## 정리

### 새로 알게된 것

- [type mapping 규칙](http://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm)
- `@LastModifiedDate`는 마지막으로 수정될 때 자동으로 값을 수정해주는 것 같음
- Enum을 가지는 경우에 `@Enumerated(EnumType.STRING)`을 하면 Enum 값 자체가 DB에 들어감. 다른 것을 ORDINAL가 있음
- `email varchar(50)`과 같이 길이를 정할 때는 `@Column(length = 50)`

### 저장만 해도 객체의 id가 생기는 이유

```java
class UserRepositoryTest {
    @Test
    void save() {
        User user = new User("알린", "12345678", "장원영", "ozragwort@gmail.com");

        User actual = users.save(user);

        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(user.getId()),
                () -> assertThat(actual.getUserId()).isEqualTo(user.getUserId())
        );
    }
}
```

user는 save되기 전까지 id값이 null이다.

그런데 왜 save가 되면 user의 아이디도 값이 생길까?

save의 과정을 본 뒤 나의 생각
1. 영속성 컨텍스트의 1차 캐시에 값을 저장함
2. 1차 캐시에 id가 null인 값이 저장될 수 없음
3. 따라서 db에 저장하고 id가 포함된 객체가 1차 캐시에 저장됨
4. 따라서 user와 actual은 동등하고 동일한 객체가 되고 id가 null이 아니게 됨
