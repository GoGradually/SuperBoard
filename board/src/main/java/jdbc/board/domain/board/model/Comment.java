package jdbc.board.domain.board.model;

import jdbc.board.domain.board.exception.InvalidContentsException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
public class Comment {
    private Long id;
    private Post post;
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
