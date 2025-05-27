package jdbc.board.application.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostLine {
    @Schema(description = "게시글의 ID")
    private Long postId;
    @Schema(description = "게시글의 제목")
    private String postTitle;
    @Schema(description = "게시글의 댓글 수")
    private Long commentCount;

    public PostLine() {
    }

    public PostLine(Long postId, String postTitle, Long commentCount) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.commentCount = commentCount;
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
