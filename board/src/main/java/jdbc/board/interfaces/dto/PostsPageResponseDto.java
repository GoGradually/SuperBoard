package jdbc.board.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;

import java.util.List;

@Schema(description = "조회 페이지의 정보 DTO")
public class PostsPageResponseDto {
    @Schema(description = "존재하는 내부 댓글을 관리하는 리스트")
    private final List<PostLine> postLines;
    @Schema(description = "현재 페이지의 정보와 페이지 블록 정보를 담은 DTO")
    private final PageState pageState;

    public PostsPageResponseDto(List<PostLine> postLines, PageState pageState) {
        this.postLines = postLines;
        this.pageState = pageState;
    }

    public List<PostLine> getPostLines() {
        return postLines;
    }

    public PageState getPageState() {
        return pageState;
    }
}
