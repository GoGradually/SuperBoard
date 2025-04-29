package jdbc.board.domain.board.model;

import jdbc.board.domain.board.event.CommentCreatedDomainEvent;
import jdbc.board.domain.board.event.CommentDeletedDomainEvent;
import jdbc.board.domain.board.event.CommentUpdatedDomainEvent;
import jdbc.board.domain.board.exception.CommentNotFoundException;
import jdbc.board.domain.board.exception.InvalidContentsException;
import jdbc.board.domain.board.exception.InvalidTitleException;
import jdbc.board.domain.shared.DomainEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Post {
    private Long id;
    private String title;
    private String contents;
    private final List<Comment> comments = new ArrayList<>();
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Post(String title, String contents) {
        validateTitle(title);
        validateContents(contents);
        this.title = title;
        this.contents = contents;
    }

    public void changeTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changeContents(String newContents) {
        validateContents(newContents);
        this.contents = newContents;
    }

    public void addComment(String contents) {
        Comment comment = new Comment(contents, this);
        comments.add(comment);
        domainEvents.add(new CommentCreatedDomainEvent(comment));
    }

    /**
     * 외부 comment가 추가되더라도
     * 실제 이벤트 퍼블리셔에 코멘트 생성 이벤트가 추가되진 않음
     * 실제 영속성 데이터에 영향을 끼치진 않지만, 리포지토리에서 Post 애그리거트를 형성하기 위해 필요한 코드.
     * @param commentId db에서 가져온 comment_id
     * @param contents db에서 가져온 댓글의 내용
     */
    public void attachComment(Long commentId, String contents){
        Comment comment = new Comment(commentId, this, contents);
        comments.add(comment);
    }

    // TODO 쿼리 모델로 변경 요망
    public Comment findComment(Long commentId) {
        return comments.stream().filter(c -> c.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    public void changeCommentContents(Long commentId, String newContents) {
        Comment comment = comments.stream().filter(cmt -> cmt.getId().equals(commentId)).findFirst()
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다."));
        comment.changeContents(newContents);
        domainEvents.add(new CommentUpdatedDomainEvent(comment));
    }

    public void removeComment(Long commentId) {
        Comment comment = comments.stream().filter(cmt -> cmt.getId().equals(commentId)).findFirst().orElseThrow(
                () -> new CommentNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );
        comments.remove(comment);
        domainEvents.add(new CommentDeletedDomainEvent(comment));
    }

    private static void validateTitle(String title) {
        if (title.isBlank()) {
            throw new InvalidTitleException("제목은 비어 있을 수 없습니다.");
        }
    }

    private static void validateContents(String contents) {
        if (contents.isBlank()) {
            throw new InvalidContentsException("댓글은 비어 있을 수 없습니다.");
        }
    }
}
