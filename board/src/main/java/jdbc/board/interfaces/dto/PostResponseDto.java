package jdbc.board.interfaces.dto;

import jdbc.board.domain.board.model.Post;

import java.util.List;

public class PostResponseDto {
    List<CommentResponseDto> comments;
    private Long id;
    private String title;
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
