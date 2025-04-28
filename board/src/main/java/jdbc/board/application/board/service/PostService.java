package jdbc.board.application.board.service;

import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    public Post findPostDetails(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public List<PostLine> findAllPostLines(int page, int pageSize) {
        return postQueryRepository.findAllPostLines(page, pageSize);
    }
}
