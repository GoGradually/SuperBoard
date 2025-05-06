package jdbc.board.application.board.service;

import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    public static final int PAGE_SIZE = 10;
    public static final int BLOCK_SIZE = 10;
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    public Post findPostDetails(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public List<PostLine> findAllPostLines(int page) {
        return postQueryRepository.findAllPostLines(page - 1, PAGE_SIZE);
    }

    public PageState findPageState(int currentPage) {
        long totalPosts = postQueryRepository.countAllPosts();
        int totalPages = Math.max((int) Math.ceil((double) totalPosts / PAGE_SIZE), 1);

        int currentBlock = (currentPage - 1) / BLOCK_SIZE;
        int startPage = currentBlock * BLOCK_SIZE + 1;
        int endPage = Math.min(startPage + BLOCK_SIZE - 1, totalPages);

        int prevBlockPage = Math.max(startPage - 1, 1);
        int nextBlockPage = Math.min(endPage + 1, totalPages);

        return new PageState(currentPage, totalPages, startPage, endPage, prevBlockPage, nextBlockPage);
    }

    public Comment findCommentDetails(Long postId, Long commentId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."))
                .findComment(commentId);
    }

    @Transactional
    public Post writePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long postId, String title, String contents) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.changeTitle(title);
        post.changeContents(contents);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public Post writeComment(Long postId, String contents) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.addComment(contents);
        return postRepository.save(post);
    }

    @Transactional
    public Post updateComment(Long postId, Long commentId, String contents) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.changeCommentContents(commentId, contents);
        return postRepository.save(post);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.removeComment(commentId);
        postRepository.save(post);
    }
}
