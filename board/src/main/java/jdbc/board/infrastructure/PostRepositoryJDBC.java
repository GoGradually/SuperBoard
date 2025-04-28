package jdbc.board.infrastructure;

import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepositoryJDBC implements PostRepository {
    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Post> findById(Long id) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("id", id);
        ResultSetExtractor<Post> postResultSetExtractor = getPostResultSetExtractor();
        String sql = """
                select
                    post.*,
                    comment.id as comment_id,
                    comment.contents as comment_contents,
                from post
                left join comment on comment.post_id = post.id
                where post.id = :id
                """;
        return Optional.ofNullable(template.query(sql, sqlParameterSource, postResultSetExtractor));
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private static ResultSetExtractor<Post> getPostResultSetExtractor() {
        return (rs) -> {
            Post post = null;

            while (rs.next()) {
                if (post == null) {
                    post = new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("contents")
                    );
                }
                Long commentId = rs.getObject("comment_id", Long.class);
                if(commentId != null) {
                    String commentContent = rs.getString("comment_contents");
                    post.attachComment(commentId, commentContent);
                }
            }
            return post;
        };
    }


}
