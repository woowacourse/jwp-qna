# jwp-qna

## 기능 목록 
- [x] answer entity, repository 클래스 생성 
- [x] delete_history entity, repository 클래스 생성 
- [x] question entity, repository 클래스 생성 
- [x] user entity, repository 클래스 생성

### 3단계 - 질문 삭제하기 리팩터링
`QuaService.deleteQuestion()`

- [ ] 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 `deleted`를 변경한다.
- [ ] 로그인 사용자와 질문한 사람이 같은 경우만 삭제 가능하다. 
- [ ] 질문자와 답변 글의 모든 답변자가 같은 경우 삭제가 가능하다.
- [ ] 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
- [ ] 답변이 없는 경우 삭제가 가능한다. 
- [ ] 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 `deleted`를 변경한다.  
- [ ] 질문과 답변 삭제 이력을 DeleteHistory를 활용해 남긴다. 
- [ ] QnaServiceTest의 모든 테스트가 통과해야 한다. 
