package jdbc.board.infrastructure.config;

import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.application.port.EventPublisher;
import jdbc.board.domain.board.repository.PostRepository;
import jdbc.board.infrastructure.repository.PostQueryRepositoryJDBCV4;
import jdbc.board.infrastructure.repository.PostRepositoryJDBCV0;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public PostRepository postRepository(NamedParameterJdbcTemplate template, EventPublisher eventPublisher) {
        return new PostRepositoryJDBCV0(template, eventPublisher);
    }

    @Bean
    public PostQueryRepository postQueryRepository(NamedParameterJdbcTemplate template) {
        return new PostQueryRepositoryJDBCV4(template);
    }
}
