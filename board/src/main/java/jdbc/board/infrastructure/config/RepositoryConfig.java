package jdbc.board.infrastructure.config;

import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.infrastructure.repository.PostQueryRepositoryJDBCV4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public PostQueryRepository postQueryRepository(NamedParameterJdbcTemplate template) {
        return new PostQueryRepositoryJDBCV4(template);
    }
}
