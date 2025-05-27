package jdbc.board.application.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PageState {
    @Schema(description = "현재 사용자가 위치하고 있는 페이지")
    private final int currentPage;
    @Schema(description = "전체 페이지 수")
    private final int totalPages;
    @Schema(description = "현재 블록의 시작 페이지")
    private final int startPage;
    @Schema(description = "현재 블록의 종료 페이지")
    private final int endPage;
    @Schema(description = "이전 블록으로 가는 페이지 번호")
    private final int prevBlockPage;
    @Schema(description = "다음 블록으로 가는 페이지 번호. 최대 페이지 수보다 클 수 없다.")
    private final int nextBlockPage;

    public PageState(int currentPage, int totalPages, int startPage, int endPage, int prevBlockPage, int nextBlockPage) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.startPage = startPage;
        this.endPage = endPage;
        this.prevBlockPage = prevBlockPage;
        this.nextBlockPage = nextBlockPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getPrevBlockPage() {
        return prevBlockPage;
    }

    public int getNextBlockPage() {
        return nextBlockPage;
    }
}
