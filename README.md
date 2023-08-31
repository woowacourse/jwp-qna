# jwp-qna

## 기능 요구사항

- [x] 사용자가 질문을 삭제하면 데이터의 상태만 삭제 상태(deleted - boolean type)로 변경한다.
  - [x] 질문 삭제시 답변을 함께 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
  - 삭제가 가능한 경우
    - [x] 질문자만 질문을 삭제할 수 있다.
    - [x] 답변이 없는 경우 삭제가 가능하다.
    - [x] 모든 답변자가 질문자와 같은 경우 삭제가 가능하다.
  - 삭제 불가능한 경우
    - [x] 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
- [x] 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.