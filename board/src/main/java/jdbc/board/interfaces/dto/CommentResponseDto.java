package jdbc.board.interfaces.dto;

import jdbc.board.domain.board.model.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private String contents;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.contents = comment.getContents();
    }
}
