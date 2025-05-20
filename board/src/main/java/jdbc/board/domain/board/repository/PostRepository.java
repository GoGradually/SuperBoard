package jdbc.board.domain.board.repository;

import jdbc.board.domain.board.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join fetch Comment c on p = c.post where p.id = :postId")
    Optional<Post> findById(Long postId);
}
