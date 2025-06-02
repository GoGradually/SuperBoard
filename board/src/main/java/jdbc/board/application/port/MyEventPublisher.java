package jdbc.board.application.port;

import jdbc.board.domain.shared.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;

public class MyEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher publisher;

    public MyEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
