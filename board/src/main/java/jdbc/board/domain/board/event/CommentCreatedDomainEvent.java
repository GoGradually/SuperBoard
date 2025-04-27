package jdbc.board.domain.board.event;

import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.shared.DomainEvent;
import lombok.Getter;

@Getter
public class CommentCreatedDomainEvent implements DomainEvent {
    private Comment comment;

    public CommentCreatedDomainEvent(Comment comment) {
        this.comment = comment;
    }
}
