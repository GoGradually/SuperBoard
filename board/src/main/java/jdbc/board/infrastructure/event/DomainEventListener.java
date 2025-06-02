package jdbc.board.infrastructure.event;

import jdbc.board.domain.shared.DomainEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DomainEventListener {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(DomainEvent event) {
        // TODO 이벤트 퍼블리싱
    }
}
