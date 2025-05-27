package jdbc.board.application.board.service;

import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.exception.PageOverflowedException;
import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.application.port.EventPublisher;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PostService {
    public static final int PAGE_SIZE = 10;
    public static final int BLOCK_SIZE = 10;
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final EventPublisher eventPublisher;

    public PostService(PostRepository postRepository, PostQueryRepository postQueryRepository, EventPublisher eventPublisher) {
        this.postRepository = postRepository;
        this.postQueryRepository = postQueryRepository;
        this.eventPublisher = eventPublisher;
    }

    public Post findPostDetails(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));
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
        post.getDomainEvents().forEach(eventPublisher::publish);
        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public void writeComment(Long postId, String contents) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.addComment(contents);
        post.getDomainEvents().forEach(eventPublisher::publish);
    }

    @Transactional
    public Post updateComment(Long postId, Long commentId, String contents) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.changeCommentContents(commentId, contents);
        post.getDomainEvents().forEach(eventPublisher::publish);
        return post;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("해당 게시글을 찾을 수 없습니다."));
        post.removeComment(commentId);
        post.getDomainEvents().forEach(eventPublisher::publish);
    }

    public List<PostLine> findAllPostLines(int page) {
        long totalPosts = postQueryRepository.countAllPosts();
        int totalPageCount = findTotalPageCount((double) totalPosts);
        validateCurrentPage(page, totalPageCount);
        return postQueryRepository.findAllPostLines(page - 1, PAGE_SIZE);
    }

    public PageState findPageState(int currentPage) {
        long totalPostCount = postQueryRepository.countAllPosts();
        int totalPageCount = findTotalPageCount((double) totalPostCount);
        validateCurrentPage(currentPage, totalPageCount);

        PageBlockInfo currentPageBlock = getPageBlockInfo(currentPage, totalPageCount);
        return new PageState(currentPage, totalPageCount, currentPageBlock.startPage(), currentPageBlock.endPage(), currentPageBlock.prevBlockPage(), currentPageBlock.nextBlockPage());
    }

    private static PageBlockInfo getPageBlockInfo(int currentPage, int totalPageCount) {
        int currentBlock = (currentPage - 1) / BLOCK_SIZE;
        int startPage = currentBlock * BLOCK_SIZE + 1;
        int endPage = Math.min(startPage + BLOCK_SIZE - 1, totalPageCount);

        int prevBlockPage = Math.max(startPage - 1, 1);
        int nextBlockPage = Math.min(endPage + 1, totalPageCount);
        return new PageBlockInfo(startPage, endPage, prevBlockPage, nextBlockPage);
    }

    private static int findTotalPageCount(double totalPosts) {
        return Math.max((int) Math.ceil(totalPosts / PAGE_SIZE), 1);
    }

    private static void validateCurrentPage(int currentPage, int totalPages) {
        if (totalPages < currentPage || currentPage < 1) {
            throw new PageOverflowedException("존재하지 않는 페이지 입니다.");
        }
    }
    private record PageBlockInfo(int startPage, int endPage, int prevBlockPage, int nextBlockPage) {


    }
}







