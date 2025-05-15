package jdbc.board.domain.board.model;

import jdbc.board.domain.board.exception.InvalidContentsException;
import jdbc.board.domain.shared.Id;

import java.util.Objects;

public class Comment {
    @Id
    private Long id;
    private final Post post;
    private String contents;

    Comment(String contents, Post post) {
        validateContents(contents);
        this.contents = contents;
        this.post = post;
    }

    Comment(Long id, Post post, String contents) {
        this.id = id;
        this.post = post;
        this.contents = contents;
    }

    void changeContents(String contents) {
        validateContents(contents);
        this.contents = contents;
    }

    public Long getId() {
        return id;
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
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private static void validateContents(String contents) {
        if (contents.isBlank()) {
            throw new InvalidContentsException("댓글은 비어 있을 수 없습니다.");
        }
    }
}
