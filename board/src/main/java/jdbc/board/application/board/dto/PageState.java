package jdbc.board.application.board.dto;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPrevBlockPage() {
        return prevBlockPage;
    }

    public void setPrevBlockPage(int prevBlockPage) {
        this.prevBlockPage = prevBlockPage;
    }

    public int getNextBlockPage() {
        return nextBlockPage;
    }

    public void setNextBlockPage(int nextBlockPage) {
        this.nextBlockPage = nextBlockPage;
    }
}
