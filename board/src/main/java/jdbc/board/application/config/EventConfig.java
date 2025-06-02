package jdbc.board.application.config;

import jdbc.board.application.port.EventPublisher;
import jdbc.board.application.port.MyEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    @Bean
    public EventPublisher eventPublisher(ApplicationEventPublisher eventPublisher) {
        return new MyEventPublisher(eventPublisher);
    }
}
