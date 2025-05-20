package jdbc.board.application.board.service;

import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.repository.PostQueryRepository;
import jdbc.board.application.port.EventPublisher;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Comment;
import jdbc.board.domain.board.model.Post;
import jdbc.board.domain.board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static jdbc.board.application.board.service.PostService.PAGE_SIZE;
import static jdbc.board.utils.JdbcUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    PostQueryRepository postQueryRepository;
    @Mock
    EventPublisher eventPublisher;

    @InjectMocks
    private PostService postService;

    @Test
    void findPostDetails_성공() {
        // given
        Post samplePost = getPost(1L, "hello", "hi");
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));

        // when
        Post byId = postService.findPostDetails(samplePost.getId());

        // then
        verify(postRepository).findById(1L);
        assertEquals(samplePost, byId);
    }

    @Test
    void findPostDetails_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.findPostDetails(1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("게시글을 찾을 수 없습니다.");
    }

    @Test
    void findAllPostLines_성공() {
        // given
        when(postQueryRepository.findAllPostLines(0, PAGE_SIZE))
                .thenReturn(List.of(new PostLine(1L, "hi", 10L)));
        // when
        List<PostLine> allPostLines = postService.findAllPostLines(1);

        // then
        verify(postQueryRepository).findAllPostLines(0, PAGE_SIZE);
        assertEquals(1, allPostLines.size());
        PostLine postLine = allPostLines.get(0);
        assertThat(postLine.getPostId()).isEqualTo(1L);
        assertThat(postLine.getPostTitle()).isEqualTo("hi");
        assertThat(postLine.getCommentCount()).isEqualTo(10L);
    }

    @Test
    void findPageState_게시글_없음_1페이지() {
        // given
        Long postCount = 0L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(currentPage);
        assertThat(pageState.getStartPage()).isEqualTo(currentPage);
        assertThat(pageState.getEndPage()).isEqualTo(currentPage);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(currentPage);
        assertThat(pageState.getNextBlockPage()).isEqualTo(currentPage);
    }

    @Test
    void findPageState_게시글_1개() {
        // given
        Long postCount = 1L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(1);
        assertThat(pageState.getStartPage()).isEqualTo(1);
        assertThat(pageState.getEndPage()).isEqualTo(1);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(1);
        assertThat(pageState.getNextBlockPage()).isEqualTo(1);
    }

    @Test
    void findPageState_게시글_10개() {
        // given
        Long postCount = 10L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(1);
        assertThat(pageState.getStartPage()).isEqualTo(1);
        assertThat(pageState.getEndPage()).isEqualTo(1);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(1);
        assertThat(pageState.getNextBlockPage()).isEqualTo(1);
    }

    @Test
    void findPageState_게시글_11개() {
        // given
        Long postCount = 11L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(2);
        assertThat(pageState.getStartPage()).isEqualTo(1);
        assertThat(pageState.getEndPage()).isEqualTo(2);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(1);
        assertThat(pageState.getNextBlockPage()).isEqualTo(2);
    }

    @Test
    void findPageState_게시글_100개() {
        // given
        Long postCount = 100L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(10);
        assertThat(pageState.getStartPage()).isEqualTo(1);
        assertThat(pageState.getEndPage()).isEqualTo(10);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(1);
        assertThat(pageState.getNextBlockPage()).isEqualTo(10);
    }

    @Test
    void findPageState_게시글_101개() {
        // given
        Long postCount = 101L;
        int currentPage = 1;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(11);
        assertThat(pageState.getStartPage()).isEqualTo(1);
        assertThat(pageState.getEndPage()).isEqualTo(10);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(1);
        assertThat(pageState.getNextBlockPage()).isEqualTo(11);
    }

    @Test
    void findPageState_게시글_201개_15페이지() {
        // given
        Long postCount = 201L;
        int currentPage = 15;
        when(postQueryRepository.countAllPosts()).thenReturn(postCount);

        // when
        PageState pageState = postService.findPageState(currentPage);

        // then
        assertThat(pageState.getCurrentPage()).isEqualTo(currentPage);
        assertThat(pageState.getTotalPages()).isEqualTo(21);
        assertThat(pageState.getStartPage()).isEqualTo(11);
        assertThat(pageState.getEndPage()).isEqualTo(20);
        assertThat(pageState.getPrevBlockPage()).isEqualTo(10);
        assertThat(pageState.getNextBlockPage()).isEqualTo(21);
    }

    @Test
    void findCommentDetails_성공() {
        // given
        long postId = 1L;
        long commentId = 1L;
        Post commentedPost = getPost(postId, "hello", "hi");
        commentedPost.attachComment(commentId, "haha");
        when(postRepository.findById(postId)).thenReturn(Optional.of(commentedPost));

        // when
        Comment commentDetails = postService.findCommentDetails(postId, commentId);

        // then
        verify(postRepository).findById(postId);
        assertThat(commentDetails.getId()).isEqualTo(commentId);
    }

    @Test
    void findCommentDetails_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.findCommentDetails(1L, 1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("게시글을 찾을 수 없습니다.");
    }

    @Test
    void writePost_성공() {
        // given
        Post post = getSamplePost();
        when(postRepository.save(post)).thenReturn(post);

        // when
        Post saved = postService.writePost(post);

        // then
        verify(postRepository).save(post);
        assertEquals(post, saved);
    }

    @Test
    void updatePost_성공() {
        // given
        long postId = 1L;
        String oldTitle = "hello";
        String oldContents = "hi";
        String newTitle = "wow";
        String newContents = "amazing";
        Post post = getPost(postId, oldTitle, oldContents);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        postService.updatePost(postId, newTitle, newContents);

        // then
        verify(postRepository).findById(postId);
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContents()).isEqualTo(newContents);

    }

    @Test
    void updatePost_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.updatePost(1L, "newTitle", "newContents"))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("해당 게시글을 찾을 수 없습니다.");
    }

    @Test
    void deletePost_성공() {
        // given
        long id = 1L;

        // when
        postService.deletePost(id);

        // then
        verify(postRepository).deleteById(id);
    }

    @Test
    void writeComment_성공() {
        // given
        Post post = getSamplePost();
        String commentContents = "hello";
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        postService.writeComment(post.getId(), commentContents);

        // then
        assertThat(post.getDomainEvents()).hasSize(1);
        assertThat(post.getComments()).hasSize(1);

        Comment comment = post.getComments().getFirst();
        assertThat(comment.getContents()).isEqualTo(commentContents);
    }

    @Test
    void writeComment_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.writeComment(1L, "hello"))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("해당 게시글을 찾을 수 없습니다.");
    }


    @Test
    void updateComment_성공() {
        // given
        Post post = getCommentedPost();
        Long commentId = 1L;
        String newCommentContents = "hello";
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        postService.updateComment(post.getId(), commentId, newCommentContents);

        // then
        assertThat(post.getDomainEvents()).hasSize(1);

        Comment comment = post.findComment(commentId);
        assertThat(comment.getContents()).isEqualTo(newCommentContents);
    }

    @Test
    void updateComment_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.updateComment(1L, 1L, "hi"))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("해당 게시글을 찾을 수 없습니다.");
    }

    @Test
    void deleteComment_성공() {
        // given
        long postId = 1L;
        long commentId = 1L;
        Post post = getPost(postId, "title", "postContents");
        post.attachComment(commentId, "hello");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        postService.deleteComment(postId, commentId);

        // then
        assertThat(post.getId()).isEqualTo(postId);
        assertThat(post.getDomainEvents()).hasSize(1);
        assertThat(post.getComments()).hasSize(0);
    }

    @Test
    void deleteComment_게시글_없음() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThatThrownBy(() -> postService.deleteComment(1L, 1L))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("해당 게시글을 찾을 수 없습니다.");
    }
}