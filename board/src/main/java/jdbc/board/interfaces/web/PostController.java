package jdbc.board.interfaces.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.service.PostService;
import jdbc.board.domain.board.model.Post;
import jdbc.board.interfaces.dto.CommentRequestDto;
import jdbc.board.interfaces.dto.PostRequestDto;
import jdbc.board.interfaces.dto.PostResponseDto;
import jdbc.board.interfaces.dto.PostsPageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "게시글 API", description = "게시글 CRUD를 위한 API")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 목록 조회", description = "페이지 번호를 이용하여 적절한 페이지 내의 게시글을 탐색하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "해당 페이지의 상태와 존재하는 게시글 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostsPageResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재할 수 없는 페이지를 검색하려 할 경우")
    })
    @GetMapping("/post")
    public ResponseEntity<PostsPageResponseDto> post(@RequestParam(required = false, defaultValue = "1") int page) {
        List<PostLine> allPostLines = postService.findAllPostLines(page);
        PageState pageState = postService.findPageState(page);
        PostsPageResponseDto postsPageResponseDto = new PostsPageResponseDto(allPostLines, pageState);
        return ResponseEntity.ok(postsPageResponseDto);
    }

    @Operation(summary = "특정 게시글 조회", description = "게시글 ID를 통해 특정 게시글의 내용 및 댓글을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "해당 게시글의 내용과 존재하는 댓글들을 반환",
                    content = @Content(schema = @Schema(implementation = PostResponseDto.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없는 경우")
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        Post post = postService.findPostDetails(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    @Operation(summary = "새로운 게시글 생성", description = "새로운 게시글을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "생성된 게시글의 URI를 반환한다.",
                    content = @Content(schema = @Schema(implementation = URI.class))),
            @ApiResponse(responseCode = "400", description = "적절한 게시글의 조건을 만족하지 못했을 경우 - 글자 수 제한, 예외 메시지 확인")
    })
    @PostMapping("/post")
    public ResponseEntity<URI> newPost(@RequestBody PostRequestDto postDto) {
        Post post = postService.writePost(new Post(postDto.getTitle(), postDto.getContents()));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/{id}").build(post.getId());
        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "게시글 수정", description = "특정 게시글의 제목/내용을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "게시글의 정보 수정에 성공했을 경우, 변경된 게시글 정보를 반환한다.",
                    content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "적절한 게시글의 조건을 만족하지 못했을 경우 - 글자 수 제한, 예외 메시지 확인"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없는 경우. 예외 메시지 확인")
    })
    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable Long id, @RequestBody PostRequestDto postDto) {
        Post post = postService.updatePost(id, postDto.getTitle(), postDto.getContents());
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    @Operation(summary = "댓글 생성", description = "특정 게시글에 댓글을 추가한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "댓글 생성에 성공했을 경우, 댓글이 작성된 게시글의 URI를 반환한다.",
                    content = @Content(schema = @Schema(implementation = URI.class))),
            @ApiResponse(responseCode = "400", description = "적절한 댓글의 조건을 만족하지 못했을 경우 - 글자 수 제한, 예외 메시지 확인"),
            @ApiResponse(responseCode = "404", description = "댓글이 작성될 게시글을 찾을 수 없는 경우. 예외 메시지 확인")
    })
    @PostMapping("/post/{id}/comments")
    public ResponseEntity<URI> newComment(@PathVariable Long id, @RequestBody CommentRequestDto commentDto) {
        postService.writeComment(id, commentDto.getContents());
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/{id}")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "댓글 수정", description = "특정 게시글에 달린 특정 댓글을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "해당 댓글의 수정에 성공했을 경우.",
                    content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "적절한 댓글의 조건을 만족하지 못했을 경우 - 글자 수 제한, 예외 메시지 확인"),
            @ApiResponse(responseCode = "404", description = "해당 게시글 혹은 댓글을 찾을 수 없는 경우. 예외 메시지 확인")
    })
    @PutMapping("/post/{id}/comments/{commentId}")
    public ResponseEntity<PostResponseDto> editComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentRequestDto commentDto) {
        Post post = postService.updateComment(id, commentId, commentDto.getContents());
        return ResponseEntity.ok(new PostResponseDto(post));
    }

    @Operation(summary = "댓글 삭제", description = "특정 게시글에 달린 특정 댓글을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "댓글 삭제를 수행한다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 게시글 혹은 댓글을 찾을 수 없는 경우. 예외 메시지 확인")
    })
    @DeleteMapping("/post/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        postService.deleteComment(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "게시글 삭제를 수행한다.", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없는 경우. 예외 메시지 확인")
    })
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
