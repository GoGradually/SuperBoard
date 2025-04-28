package jdbc.board.application.port;


import jdbc.board.domain.shared.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}
