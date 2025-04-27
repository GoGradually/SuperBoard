package jdbc.board.domain.board.repository;

import jdbc.board.domain.board.model.Post;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);
    Post save(Post post);
    void deleteById(Long id);
}
