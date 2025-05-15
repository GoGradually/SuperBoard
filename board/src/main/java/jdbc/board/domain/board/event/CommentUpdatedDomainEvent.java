package jdbc.board.domain.board.event;

import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.shared.DomainEvent;

public class CommentUpdatedDomainEvent implements DomainEvent {
    private final Comment comment;

    public CommentUpdatedDomainEvent(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
