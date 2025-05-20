package jdbc.board.application.board.exception;

public class PageOverflowedException extends RuntimeException {
    public PageOverflowedException(String message) {
        super(message);
    }
}
