package jdbc.board.application.board.service;

import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Post writePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public Post writeComment(Post post, String contents){
        post.addComment(contents);
        return postRepository.save(post);
    }

    @Transactional
    public Post updateComment(Post post, Long commentId, String contents){
        post.changeCommentContents(commentId, contents);
        return postRepository.save(post);
    }

    @Transactional
    public void deleteComment(Post post, Long commentId) {
        post.removeComment(commentId);
        postRepository.save(post);
    }
}
