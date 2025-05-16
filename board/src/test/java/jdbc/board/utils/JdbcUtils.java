package jdbc.board.utils;

import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.board.model.Post;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;


public class JdbcUtils {

    public static Post getSamplePost() {
        return getPost(1, "title", "contents");
    }

    public static Post getPost(long id, String title, String contents) {
        Post post = new Post(title, contents);
        setId(post, id);
        return post;
    }

    public static Post getCommentedPost() {
        Post samplePost = getSamplePost();
        for (int i = 1; i <= 100; i++) {
            samplePost.attachComment((long) i, "contents" + i);
        }
        return samplePost;
    }

    public static Constructor<Comment> getCommentConstructor() {
        Constructor<Comment> declaredConstructor;
        try {
            declaredConstructor = Comment.class.getDeclaredConstructor(String.class, Post.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        declaredConstructor.setAccessible(true);
        return declaredConstructor;
    }

    private static void setId(Object entity, long i) {
        ReflectionTestUtils.setField(entity, "id", i);
    }
}
