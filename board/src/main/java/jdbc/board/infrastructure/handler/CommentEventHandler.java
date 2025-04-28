package jdbc.board.infrastructure.handler;

import jdbc.board.domain.board.event.CommentCreatedDomainEvent;
import jdbc.board.domain.board.event.CommentDeletedDomainEvent;
import jdbc.board.domain.board.event.CommentUpdatedDomainEvent;
import jdbc.board.infrastructure.repository.CommentDaoJDBC;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentEventHandler {
    private final CommentDaoJDBC commentRepository;

    @EventListener
    public void onUpdatedEvent(CommentUpdatedDomainEvent event) {
        commentRepository.save(event.getComment());
    }

    @EventListener
    public void onCreatedEvent(CommentCreatedDomainEvent event) {
        commentRepository.save(event.getComment());
    }

    @EventListener
    public void onDeletedEvent(CommentDeletedDomainEvent event) {
        commentRepository.delete(event.getComment());
    }
}
