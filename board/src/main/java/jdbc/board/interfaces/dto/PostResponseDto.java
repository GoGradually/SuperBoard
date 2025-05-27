package jdbc.board.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jdbc.board.domain.board.model.Post;

import java.util.List;

@Schema(description = "생성/수정된 게시글의 제목/내용을 담은 DTO")
public class PostResponseDto {
    @Schema(description = "댓글의 목록")
    List<CommentResponseDto> comments;
    @Schema(description = "게시글의 ID")
    private Long id;
    @Schema(description = "게시글의 제목")
    private String title;
    @Schema(description = "게시글의 내용")
    private String contents;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).toList();
    }

    public List<CommentResponseDto> getComments() {
        return comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
