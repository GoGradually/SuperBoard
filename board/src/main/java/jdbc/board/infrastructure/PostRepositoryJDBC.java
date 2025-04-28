package jdbc.board.infrastructure;

import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Objects;
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
                    comment.contents as comment_contents
                from post
                left join comment on comment.post_id = post.id
                where post.id = :id
                """;
        return Optional.ofNullable(template.query(sql, sqlParameterSource, postResultSetExtractor));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() != null) {
            return merge(post);
        }
        return insert(post);
    }

    @Override
    public void deleteById(Long id) {
        String sql = """
                delete from post where id = :id
                """;
        int updated = template.update(sql, new MapSqlParameterSource("id", id));
        if (updated != 1) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }
    }

    private Post insert(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                insert into post (title, contents) values (:title, :contents)
                """;
        template.update(sql, getSqlParameterSource(post), keyHolder);
        fillPostId(post, keyHolder);
        return post;
    }

    private Post merge(Post post) {
        String sql = """
                update post set title = :title, contents = :contents
                where id = :id
                """;
        SqlParameterSource sqlParameterSource = getSqlParameterSource(post);
        int updated = template.update(sql, sqlParameterSource);
        if(updated != 1) {
            throw new PostNotFoundException("해당 게시글을 찾을 수 없습니다.");
        }
        return post;
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

    private static void fillPostId(Post post, KeyHolder keyHolder) {
        try {
            Field id = Post.class.getDeclaredField("id");
            id.setAccessible(true);
            id.set(post, Objects.requireNonNull(keyHolder.getKey()).longValue());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private static MapSqlParameterSource getSqlParameterSource(Post post) {
        return new MapSqlParameterSource()
                .addValue("title", post.getTitle())
                .addValue("contents", post.getContents())
                .addValue("id", post.getId());
    }
}
