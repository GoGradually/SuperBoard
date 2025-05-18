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
 * PostQueryRepository Version.1
 * post 테이블 전체를 조인하는 것이 아닌, 서브쿼리로 특정 페이지의 레코드만 comment와 조인하도록 설정하였다.
 * group by가 전부 그룹핑을 하지 않도록 수정하였다.
 */
public class PostQueryRepositoryJDBCV1 implements PostQueryRepository {
    private final NamedParameterJdbcTemplate template;

    public PostQueryRepositoryJDBCV1(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<PostLine> findAllPostLines(int page, int pageSize) {
        String sql = """
                SELECT
                    p.id            AS post_id,
                    p.title         AS post_title,
                    COUNT(c.id)     AS comment_count
                FROM
                    (select id, title from post order by id desc limit :pageSize offset :startIndex) p
                LEFT JOIN
                    comment c
                  ON p.id = c.post_id
                GROUP BY
                    p.id,
                    p.title
                ORDER BY p.id DESC
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
