package jdbc.board.domain.board.exception;

public class InvalidContentsException extends RuntimeException {
    public InvalidContentsException(String message) {
        super(message);
    }
}
