package jdbc.board.domain.board.model;

import jdbc.board.domain.board.event.CommentCreatedDomainEvent;
import jdbc.board.domain.board.exception.CommentNotFoundException;
import jdbc.board.domain.board.exception.InvalidContentsException;
import jdbc.board.domain.board.exception.InvalidTitleException;
import jdbc.board.domain.shared.DomainEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jdbc.board.utils.JdbcUtils.getCommentedPost;
import static jdbc.board.utils.JdbcUtils.getSamplePost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostTest {

    @Test
    void changeTitle_성공() {
        // given
        Post post = getSamplePost();

        // when
        post.changeTitle("newTitle");

        // then
        assertEquals("newTitle", post.getTitle());

    }

    @Test
    void changeTitle_비어있는_제목() {
        // given
        Post post = getSamplePost();

        // when, then
        assertThatThrownBy(() -> post.changeTitle("  "))
                .isInstanceOf(InvalidTitleException.class)
                .hasMessage("제목은 비어 있을 수 없습니다.");
    }

    @Test
    void changeContents_성공() {
        // given
        Post post = getSamplePost();

        // when
        post.changeContents("newContents");

        // then
        assertEquals("newContents", post.getContents());
    }

    @Test
    void changeContents_비어있는_제목() {
        // given
        Post post = getSamplePost();

        // when, then
        assertThatThrownBy(() -> post.changeContents("  "))
                .isInstanceOf(InvalidContentsException.class)
                .hasMessage("내용은 비어 있을 수 없습니다.");
    }

    @Test
    void addComment_성공() {
        // given
        Post post = getSamplePost();

        // when
        post.addComment("newComment");

        // then
        List<DomainEvent> domainEvents = post.getDomainEvents();
        assertThat(domainEvents).hasSize(1);

        DomainEvent domainEvent = domainEvents.get(0);
        assertThat(domainEvent.getClass()).isEqualTo(CommentCreatedDomainEvent.class);

        CommentCreatedDomainEvent commentCreatedDomainEvent = (CommentCreatedDomainEvent) domainEvent;
        Comment comment = commentCreatedDomainEvent.getComment();
        assertThat(comment).isNotNull();
    }

    @Test
    void addComment_내용이_비어있음() {
        // given
        Post post = getSamplePost();

        // when
        assertThatThrownBy(() -> post.addComment("  "))
                .isInstanceOf(InvalidContentsException.class)
                .hasMessage("댓글은 비어 있을 수 없습니다.");
    }

    @Test
    void findComment_성공() {
        // given
        Post commentedPost = getCommentedPost();

        // when
        Comment comment = commentedPost.findComment(1L);

        // then
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getPost()).isEqualTo(commentedPost);
    }

    @Test
    void findComment_댓글없음() {
        // given
        Post post = getSamplePost();

        // when, then
        assertThatThrownBy(() -> post.findComment(1L))
                .hasMessage("댓글을 찾을 수 없습니다.")
                .isInstanceOf(CommentNotFoundException.class);

    }


    @Test
    void changeCommentContents_성공() {
        // given
        Post post = getSamplePost();
        post.attachComment(1L, "commentContents");

        // when
        post.changeCommentContents(1L, "newContents");

        // then
        Comment comment = post.findComment(1L);
        assertThat(comment).isNotNull();
        assertThat(comment.getContents()).isEqualTo("newContents");
    }

    @Test
    void changeCommentContents_댓글없음() {
        // given
        Post post = getSamplePost();

        // when, then
        assertThatThrownBy(() -> post.changeCommentContents(1L, "  "))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("해당 댓글을 찾을 수 없습니다.");
    }

    @Test
    void changeCommentContents_비어있는_댓글() {
        // given
        Post post = getSamplePost();
        post.attachComment(1L, "commentContents");

        // when, then
        assertThatThrownBy(() -> post.changeCommentContents(1L, "  "))
                .isInstanceOf(InvalidContentsException.class)
                .hasMessage("댓글은 비어 있을 수 없습니다.");

    }

    @Test
    void removeComment_성공() {
        // given
        Post post = getSamplePost();
        post.attachComment(1L, "commentContents");

        // when
        post.removeComment(1L);

        // then
        List<Comment> comments = post.getComments();
        assertThat(comments).isEmpty();
    }

    @Test
    void removeComment_댓글없음() {
        // given
        Post post = getSamplePost();

        // when, then
        assertThatThrownBy(() -> post.removeComment(1L))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("해당 댓글을 찾을 수 없습니다.");
    }
}