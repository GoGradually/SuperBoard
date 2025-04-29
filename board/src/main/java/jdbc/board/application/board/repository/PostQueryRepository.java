package jdbc.board.application.board.repository;

import jdbc.board.application.board.dto.PostLine;

import java.util.List;

public interface PostQueryRepository {
    List<PostLine> findAllPostLines(int page, int pageSize);

    Long countAllPosts();
}
