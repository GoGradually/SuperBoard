package jdbc.board.interfaces.web;

import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.service.PostService;
import jdbc.board.domain.board.model.Post;
import jdbc.board.interfaces.dto.CommentRequestDto;
import jdbc.board.interfaces.dto.CommentResponseDto;
import jdbc.board.interfaces.dto.PostRequestDto;
import jdbc.board.interfaces.dto.PostResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/post";
    }

    @GetMapping("/post")
    public String post(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        List<PostLine> allPostLines = postService.findAllPostLines(page);
        PageState pageState = postService.findPageState(page);
        model.addAttribute("postLines", allPostLines);
        model.addAttribute("pageState", pageState);
        return "index";
    }

    @GetMapping("/post/new")
    public String newPost() {
        return "post/newPost";
    }

    @PostMapping("/post/new")
    public String newPost(@ModelAttribute PostRequestDto postDto, RedirectAttributes redirectAttributes) {
        Post post = postService.writePost(new Post(postDto.getTitle(), postDto.getContents()));
        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Post post = postService.findPostDetails(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        model.addAttribute("post", postResponseDto);
        return "post/index";
    }

    @GetMapping("/post/{id}/edit")
    public String editPostPage(@PathVariable Long id, Model model) {
        Post post = postService.findPostDetails(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        model.addAttribute("post", postResponseDto);
        return "post/editPost";
    }

    @PostMapping("/post/{id}/edit")
    public String editPost(@PathVariable Long id, @ModelAttribute PostRequestDto postDto, RedirectAttributes redirectAttributes) {
        Post post = postService.updatePost(id, postDto.getTitle(), postDto.getContents());
        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    @PostMapping("/post/{id}/comments")
    public String newComment(@PathVariable Long id, @ModelAttribute CommentRequestDto commentDto, RedirectAttributes redirectAttributes) {
        postService.writeComment(id, commentDto.getContents());
        redirectAttributes.addAttribute("id", id);
        return "redirect:/post/{id}";
    }

    @GetMapping("/post/{id}/comments/{commentId}/edit")
    public String editCommentPage(@PathVariable Long id, @PathVariable Long commentId, Model model) {
        CommentResponseDto commentResponseDto = new CommentResponseDto(postService.findCommentDetails(id, commentId));
        model.addAttribute("comment", commentResponseDto);
        return "post/editComment";
    }

    @PostMapping("/post/{id}/comments/{commentId}/edit")
    public String editComment(@PathVariable Long id, @PathVariable Long commentId, CommentRequestDto commentDto, RedirectAttributes redirectAttributes) {
        postService.updateComment(id, commentId, commentDto.getContents());
        redirectAttributes.addAttribute("id", id);
        return "redirect:/post/{id}";
    }

    @DeleteMapping("/post/{id}/comments/{commentId}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        postService.deleteComment(id, commentId);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/post/{id}";
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/";
    }
}
