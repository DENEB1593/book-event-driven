# book-event-driven

---

### 실행
    docker-compose up -d

- book-api, book-publish, author-api, notification 으로 구성
- 동작방식
  - book-api에서 book 저장
  - book-publish는 20초 주기로 저장된 book publish
  - book-publish-listner는 publish된 book을 notification으로 메세징 

* spring 3.0.0 이상 gradle build 시 spring-core build 오류 수정 
  * 참조: https://noahhsu.medium.com/what-problems-did-i-solve-when-migrating-spring-boot-to-3-0-0-796b545ec00
* spring 3.0.0 이상 구성 시 gradle 7.4.0 이상으로 빌드 필요
