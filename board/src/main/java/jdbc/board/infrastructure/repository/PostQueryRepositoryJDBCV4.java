package jdbc.board.infrastructure.repository;

import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.repository.PostQueryRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;


/**
 * PostQueryRepository Version.4
 * Version1, 2 두가지의 개선을 합친 형태로 최적화하였다.
 */
public class PostQueryRepositoryJDBCV4 implements PostQueryRepository {
    private final NamedParameterJdbcTemplate template;

    public PostQueryRepositoryJDBCV4(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<PostLine> findAllPostLines(int page, int pageSize) {
        String sql = """
                SELECT
                    p.post_id            AS post_id,
                    p.title         AS post_title,
                    COUNT(c.id)     AS comment_count
                FROM
                    (select post_id, title from post order by post_id desc limit :pageSize offset :startIndex) p
                LEFT JOIN
                    comment c
                  ON p.post_id = c.post_id
                GROUP BY
                    p.post_id
                ORDER BY p.post_id DESC
                """;
        RowMapper<PostLine> postLineRowMapper = new BeanPropertyRowMapper<>(PostLine.class);

        return template.query(sql,
                new MapSqlParameterSource()
                        .addValue("pageSize", pageSize)
                        .addValue("startIndex", page * pageSize),
                postLineRowMapper);
    }

    @Override
    public Long countAllPosts() {
        String sql = "select count(*) from post";
        return template.queryForObject(sql, Map.of(), Long.class);
    }
}
