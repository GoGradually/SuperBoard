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
 * PostQueryRepository 최초 버전
 * 풀 스캔 left 조인 -> group by -> order by 수행하는 버전
 * 개선 과정을 담기 위한 파일
 */
public class PostQueryRepositoryJDBCV0 implements PostQueryRepository {
    private final NamedParameterJdbcTemplate template;

    public PostQueryRepositoryJDBCV0(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<PostLine> findAllPostLines(int page, int pageSize) {
        String sql = """
                SELECT
                    p.id            AS post_id,
                    p.title         AS post_title,
                    COUNT(c.id)     AS comment_count
                FROM
                    post p
                LEFT JOIN
                    comment c
                  ON p.id = c.post_id
                GROUP BY
                    p.id,
                    p.title
                ORDER BY p.id DESC
                LIMIT :pageSize
                OFFSET :startIndex
                """;
        RowMapper<PostLine> postLineRowMapper = new BeanPropertyRowMapper<>(PostLine.class);

        return template.query(sql,
                new MapSqlParameterSource()
                        .addValue("pageSize", pageSize)
                        .addValue("startIndex", page*pageSize),
                postLineRowMapper);
    }

    @Override
    public Long countAllPosts() {
        String sql = "select count(*) from post";
        return template.queryForObject(sql, Map.of(), Long.class);
    }
}
