package jdbc.board.infrastructure.repository;

import jdbc.board.domain.board.exception.CommentNotFoundException;
import jdbc.board.domain.board.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDaoJDBC {
    private final NamedParameterJdbcTemplate template;

    public void save(Comment comment) {
        if (comment.getId() != null) {
            merge(comment);
        } else {
            insert(comment);
        }
    }

    public void delete(Comment comment) {
        String sql = """
                delete from comment where id = :id
                """;
        SqlParameterSource sqlParameterSource = getSqlParameterSource(comment);
        template.update(sql, sqlParameterSource);
    }

    private void insert(Comment comment) {
        String sql = """
                insert into comment (contents, post_id) values (:contents, :post_id)
                """;
        SqlParameterSource sqlParameterSource = getSqlParameterSource(comment);
        template.update(sql, sqlParameterSource);
    }

    private void merge(Comment comment) {
        String sql = """
                update comment set contents=:contents where id=:id
                """;
        SqlParameterSource sqlParameterSource = getSqlParameterSource(comment);
        int updated = template.update(sql, sqlParameterSource);
        if (updated != 1) {
            throw new CommentNotFoundException("해당 댓글을 찾을 수 없습니다.");
        }
    }

    private static MapSqlParameterSource getSqlParameterSource(Comment comment) {
        return new MapSqlParameterSource()
                .addValue("id", comment.getId())
                .addValue("contents", comment.getContents())
                .addValue("post_id", comment.getPost().getId());
    }
}
