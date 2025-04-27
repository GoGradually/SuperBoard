Spring Data JDBC 를 사용해보기 전에, 기본적인 JDBC 문법을 복습하기 위한 토이 프로젝트이다.

### 1. 단일 테이블 상품 재고 관리 - 구현 완료

- [README.md](product-management-system/README.md)
- CRUD
- JdbcTemplate 기본 문법 학습
- [학습, 고민 내용](https://go-gradually.tistory.com/entry/JdbcTemplate%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%83%81%ED%92%88-%EA%B4%80%EB%A6%AC-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%EA%B8%B0)

### 2. 게시글-댓글 게시판 - 현재 구현 중
- [README.md](board/README.md)
- 조인
- 페이징
- 기본적인 트랜잭션 처리
- DDD를 최대한 지키면서, Child Entity를 생성/수정/삭제 처리하기 위한 방식 고민
- 애그리거트 루트에서 모든 연관 엔티티를 한꺼번에 가져오기 위한 방식 구현
  - Coarse-Grained Locking 방식의 이점 분석

### 3. CSV 파일 일괄 업로드 & 데이터 정제

- batchUpdate로 수천~수만 건 INSERT/UPDATE
- 실패 레코드 롤백·재시도 전략(예외 처리 흐름 설계)
- ResultSetMetaData 또는 DatabaseMetaData를 이용한 동적 컬럼 매핑
- 대용량 CSV 스트리밍 처리

### 4. 도서 관리 시스템

- SimpleJdbcInsert + batchUpdate 혼용
- 효율적인 트랜잭션 처리 전략 짜보기(미정)

### 5. 은행 계좌이체 시뮬레이터 - 별도 프로젝트로 관리할 예정

- 트랜잭션 전파 옵션(REQUIRED, REQUIRES_NEW 등)
- JdbcTemplate.update()를 이용한 원자적 잔액 조정
- 트랜잭션 쪼개기/합치기 별 동시성 처리량 비교
- 캐싱+배치처리 / DB 직접 처리 별 동시성 처리량 비교

# PR 형식
```
# 관련 이슈
close #n

# 변경된 점
-[x] haha

# UML 클래스 다이어그램
```
