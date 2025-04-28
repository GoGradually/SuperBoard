package jdbc.board.application.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLine {
    private Long postId;
    private String postTitle;
    private Long commentCount;
}
