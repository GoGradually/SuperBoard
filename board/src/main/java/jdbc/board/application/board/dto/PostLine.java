package jdbc.board.application.board.dto;

public class PostLine {
    private final Long postId;
    private final String postTitle;
    private final Long commentCount;

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
}
