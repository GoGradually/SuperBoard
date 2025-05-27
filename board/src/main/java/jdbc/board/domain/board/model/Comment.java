package jdbc.board.domain.board.model;

import jakarta.persistence.*;
import jdbc.board.domain.board.exception.InvalidContentsException;

import java.util.Objects;

@Entity
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String contents;

    Comment(String contents, Post post) {
        validateContents(contents);
        this.contents = contents;
        this.post = post;
    }

    protected Comment() {
    }

    Comment(Long id, Post post, String contents) {
        this.commentId = id;
        this.post = post;
        this.contents = contents;
    }

    void changeContents(String contents) {
        validateContents(contents);
        this.contents = contents;
    }

    public Long getId() {
        return commentId;
    }

    public Post getPost() {
        return post;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commentId);
    }

    private static void validateContents(String contents) {
        if (contents.isBlank()) {
            throw new InvalidContentsException("댓글은 비어 있을 수 없습니다.");
        }
        if (contents.length() > 200) {
            throw new InvalidContentsException("댓글의 길이는 200자를 초과할 수 없습니다.");
        }
    }
}
