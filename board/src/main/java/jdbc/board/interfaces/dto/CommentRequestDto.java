package jdbc.board.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성/변경을 요청하는 댓글의 생성/변경될 내용을 담은 DTO")
public class CommentRequestDto {
    @Schema(description = "원하는 댓글의 내용")
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
