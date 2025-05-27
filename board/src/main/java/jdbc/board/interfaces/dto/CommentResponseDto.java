package jdbc.board.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jdbc.board.domain.board.model.Comment;

@Schema(description = "반환되는 댓글의 정보를 담은 DTO")
public class CommentResponseDto {
    @Schema(description = "댓글의 ID")
    private Long commentId;
    @Schema(description = "댓글이 달린 게시글의 ID")
    private Long postId;
    @Schema(description = "댓글의 내용")
    private String contents;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.contents = comment.getContents();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long id) {
        this.commentId = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
