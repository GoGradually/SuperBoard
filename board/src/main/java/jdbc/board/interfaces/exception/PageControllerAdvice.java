package jdbc.board.interfaces.exception;

import jdbc.board.application.board.exception.PageOverflowedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PageControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PageOverflowedException.class)
    public String handlePageOverflowedException(PageOverflowedException e) {
        return e.getMessage();
    }
}
