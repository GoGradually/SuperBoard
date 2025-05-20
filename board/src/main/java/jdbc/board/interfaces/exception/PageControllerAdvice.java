package jdbc.board.interfaces.exception;

import jdbc.board.application.board.exception.PageOverflowedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PageControllerAdvice {
    @ExceptionHandler(PageOverflowedException.class)
    public String handlePageOverflowedException(PageOverflowedException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "404";
    }
}
