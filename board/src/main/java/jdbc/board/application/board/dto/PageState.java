package jdbc.board.application.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageState {
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int prevBlockPage;
    private int nextBlockPage;

    public PageState(int currentPage, int totalPages, int startPage, int endPage, int prevBlockPage, int nextBlockPage) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.startPage = startPage;
        this.endPage = endPage;
        this.prevBlockPage = prevBlockPage;
        this.nextBlockPage = nextBlockPage;
    }
}
