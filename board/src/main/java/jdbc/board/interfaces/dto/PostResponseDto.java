package jdbc.board.interfaces.dto;

import jdbc.board.domain.board.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
}
