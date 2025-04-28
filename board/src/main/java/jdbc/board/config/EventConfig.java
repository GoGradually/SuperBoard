package jdbc.board.config;

import jdbc.board.application.port.EventPublisher;
import jdbc.board.core.MyEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

@Configuration
public class EventConfig {
    @Bean
    public EventPublisher eventPublisher(ApplicationEventPublisher eventPublisher) {
        return new MyEventPublisher(eventPublisher);
    }
}
