CI/CD 상태 - [![CI/CD](https://github.com/GoGradually/SuperBoard/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/GoGradually/SuperBoard/actions/workflows/ci-cd.yml)

테스트 코드 커버리지 - [![codecov](https://codecov.io/gh/GoGradually/SuperBoard/graph/badge.svg?token=VFHVL9J58Q)](https://codecov.io/gh/GoGradually/SuperBoard)

# 주요 목표
### 비즈니스 관점
- **사용자 간의 실시간 소통**을 킬러 기능으로 추구한다.
- 다양한 사용자 간의 실시간 토론이 원할한 구조로 설계한다.

### 개발 관점
- 저지연, 고성능, 실시간을 추구한다.
- DDD, 클린 아키텍처를 소프트웨어 아키텍처로 갖는다.
- 분석 관점에서 OOA를 통해 작업의 순서를 결정한다.
- 설계 관점에서 TDD를 통해 요구사항을 잘게 쪼개며 테스트 코드와 함께 작업을 수행한다.


# 주요 내용

### 시스템 설계/성능 최적화 관점
- **[쿼리 최적화 - 506초->0.001초 성능 개선](https://go-gradually.tistory.com/entry/%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%8C%93%EA%B8%80-%EC%88%98-%EC%A1%B0%ED%9A%8C-%EC%BF%BC%EB%A6%AC-%EC%B5%9C%EC%A0%81%ED%99%94)**

### 객체지향 설계 관점

- [**도메인 주도 설계 적용**
  (DDD 학습 내용)](https://dev.go-gradually.me/entry/%EB%8F%84%EB%A9%94%EC%9D%B8-%EC%A3%BC%EB%8F%84-%EC%84%A4%EA%B3%84DDD%EC%9D%98-%ED%95%B5%EC%8B%AC-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC)
- [**GRASP 패턴** - 책임 분리 기준](https://go-gradually.tistory.com/entry/%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-%ED%8C%A8%EB%9F%AC%EB%8B%A4%EC%9E%84-OOD%EC%99%80-GRASP-%ED%8C%A8%ED%84%B4)
- [학습, 고민 내용 - JDBC Version](https://go-gradually.tistory.com/entry/JdbcTemplate%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EA%B2%8C%EC%8B%9C%EA%B8%80-%EB%8C%93%EA%B8%80-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%EA%B8%B0)
- [게시판 기능 요구사항](board/README.md)

# 프로젝트 구조

## Swagger API 명세

[https://api.board.go-gradually.me/swagger-ui/index.html](https://api.board.go-gradually.me/swagger-ui/index.html)

## 시스템 아키텍처 다이어그램

![system-architecture.jpg](docs/system-architecture.jpg)

## CI/CD 워크플로우

![cicd workflows.jpg](docs/cicd%20workflows.jpg)
## 도메인 모델
```mermaid
classDiagram
    class post {
        -id: Long
        -title: String
        -contents: String
        +changeTitle(String title): void
        +changeContents(String contents): void
        +addComment(String commentContents): void
        +findComment(Long commentId): Comment
        +attachComment(Long commentId, String commentContents): void
        +updateCommentContents(Long commentId, String contents): void
        +removeComment(Long commentId): void
    }
    class comment{
        -id: Long
        -contents: String
        ~changeContents(String contents): void
    }

    post "1"--"*" comment

```

## 패키지 구조

```
src/main/java/jdbc/board/
├── BoardApplication.java
├── application
│   ├── board
│   │   ├── dto
│   │   │   └── PostLine.java
│   │   ├── handler
│   │   ├── repository
│   │   │   └── PostQueryRepository.java
│   │   └── service
│   │       └── PostService.java
│   └── port
│       └── EventPublisher.java
├── config
│   └── EventConfig.java
├── core
│   └── MyEventPublisher.java
├── domain
│   ├── board
│   │   ├── event
│   │   │   ├── CommentCreatedDomainEvent.java
│   │   │   ├── CommentDeletedDomainEvent.java
│   │   │   └── CommentUpdatedDomainEvent.java
│   │   ├── exception
│   │   │   ├── CommentNotFoundException.java
│   │   │   ├── InvalidContentsException.java
│   │   │   ├── InvalidTitleException.java
│   │   │   └── PostNotFoundException.java
│   │   ├── model
│   │   │   ├── Comment.java
│   │   │   └── Post.java
│   │   └── repository
│   │       └── PostRepository.java
│   └── shared
│       └── DomainEvent.java
├── infrastructure
│   ├── handler
│   │   └── CommentEventHandler.java
│   └── repository
│       ├── CommentDaoJDBC.java
│       ├── PostQueryRepositoryJDBC.java
│       └── PostRepositoryJDBC.java
└── interfaces
    ├── dto
    │   ├── CommentRequestDto.java
    │   ├── CommentResponseDto.java
    │   ├── PostRequestDto.java
    │   └── PostResponseDto.java
    └── web
        └── PostController.java

```

# 프로젝트 관리

## PR 형식
```
# 관련 이슈
close #n

# 변경된 점
- [x] haha

# 도메인 모델
mermaid

# 패키지 구조

```

## 서비스 코어 기능
- [ ] 대댓글 기능
  - 사용자 간의 공개적 소통
  - 계층형 구조 고려중
- [ ] 조회수/추천수 순위 실시간 랭킹 기능
  - 사용자가 현재 게시판의 트렌드를 확인할 수 있도록
- [ ] 실시간 게시글/댓글 좋아요 기능 구현
  - 현재 게시글의 좋아요/싫어요 상승 추세를 파악할 수 있도록
  - 이용자가 실시간으로 소통하고 있다는 느낌 받을 수 있도록
- [ ] 첨부 파일 업로드
## 더 나은 성능 관점
- [ ] 실패 레코드 롤백/재시도 전략(백오프/예외처리 흐름 설계)
- [ ] 비동기/Primary-Secondary(master-slave)구조



---
### 구 프로젝트 - 단일 테이블 상품 재고 관리 (구현 완료)

- [README.md](legacy/product-management-system/README.md)
- CRUD
- JdbcTemplate 기본 문법 학습
- [학습, 고민 내용](https://go-gradually.tistory.com/entry/JdbcTemplate%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%83%81%ED%92%88-%EA%B4%80%EB%A6%AC-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%EA%B8%B0)
