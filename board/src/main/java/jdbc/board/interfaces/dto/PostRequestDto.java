package jdbc.board.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "생성/수정을 원하는 게시글의 정보")
public class PostRequestDto {
    @Schema(description = "생성/수정될 게시글의 제목", minLength = 1, maxLength = 200)
    private String title;
    @Schema(description = "생성/수정될 게시글의 내용", minLength = 1, maxLength = 400)
    private String contents;

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
