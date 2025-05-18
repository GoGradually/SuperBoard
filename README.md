DDD 및 단위 테스트, DB 설계 및 최적화를 학습하기 위한 토이 프로젝트이다.
# 주요 내용
- [쿼리 최적화 - 99.9999% 성능 개선](https://go-gradually.tistory.com/entry/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%8C%93%EA%B8%80-%EC%88%98-%EC%A1%B0%ED%9A%8C-%EC%BF%BC%EB%A6%AC-%EC%B5%9C%EC%A0%81%ED%99%94)
- [DDD를 최대한 지키면서, Child Entity를 생성/수정/삭제 처리하기 위한 방식 고민](https://go-gradually.tistory.com/entry/DDD-Child-Entity%EB%A5%BC-%EC%83%9D%EC%84%B1%EC%88%98%EC%A0%95%EC%82%AD%EC%A0%9C%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95)

# 1. 단일 테이블 상품 재고 관리 - 구현 완료

- [README.md](product-management-system/README.md)
- CRUD
- JdbcTemplate 기본 문법 학습
- [학습, 고민 내용](https://go-gradually.tistory.com/entry/JdbcTemplate%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%83%81%ED%92%88-%EA%B4%80%EB%A6%AC-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%EA%B8%B0)

# 2. 게시글-댓글 게시판 - 추가 개선 중
- [README.md](board/README.md)
- 조인
- 페이징
- 기본적인 트랜잭션 처리
- 애그리거트 루트에서 모든 연관 엔티티를 한꺼번에 가져오기 위한 방식 구현
- ResultSetMetaData 또는 DatabaseMetaData를 이용한 동적 컬럼 매핑
- [학습, 고민 내용](https://go-gradually.tistory.com/entry/JdbcTemplate%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EA%B2%8C%EC%8B%9C%EA%B8%80-%EB%8C%93%EA%B8%80-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%EA%B8%B0)

## 아직 구현하지 않은 것
- [ ] 실패 레코드 롤백/재시도 전략(백오프/예외처리 흐름 설계)
- [ ] 비동기/Primary-Secondary(master-slave)구조 설계
- [ ] 대댓글 기능 구현
- [ ] 게시글/댓글 좋아요 기능 구현
- [ ] 추천 순위 실시간 랭킹 기능 구현
- [ ] 조회수 순위 실시간 랭킹 기능 구현
- [ ] 첨부 파일 업데이트 기능 구현


# PR 형식
```
# 관련 이슈
close #n

# 변경된 점
- [x] haha

# 도메인 모델
mermaid

# 패키지 구조

```
