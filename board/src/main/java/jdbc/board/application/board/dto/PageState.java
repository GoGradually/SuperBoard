package jdbc.board.application.board.dto;

public class PageState {
    private final int currentPage;
    private final int totalPages;
    private final int startPage;
    private final int endPage;
    private final int prevBlockPage;
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
