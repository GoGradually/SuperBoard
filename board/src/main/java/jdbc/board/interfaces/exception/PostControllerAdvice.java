package jdbc.board.interfaces.exception;

import jdbc.board.domain.board.exception.CommentNotFoundException;
import jdbc.board.domain.board.exception.InvalidContentsException;
import jdbc.board.domain.board.exception.InvalidTitleException;
import jdbc.board.domain.board.exception.PostNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostControllerAdvice {
    @ExceptionHandler(InvalidContentsException.class)
    public String handleContentsException(InvalidContentsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "400";
    }

    @ExceptionHandler(InvalidTitleException.class)
    public String handleTitleException(InvalidTitleException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "400";
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public String handleCommentNotFoundException(CommentNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "404";
    }

    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFoundException(PostNotFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "404";
    }
}
