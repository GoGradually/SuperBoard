package jdbc.board.interfaces.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdbc.board.application.board.dto.PageState;
import jdbc.board.application.board.dto.PostLine;
import jdbc.board.application.board.exception.PageOverflowedException;
import jdbc.board.application.board.service.PostService;
import jdbc.board.domain.board.exception.PostNotFoundException;
import jdbc.board.domain.board.model.Post;
import jdbc.board.infrastructure.config.property.CorsProperties;
import jdbc.board.interfaces.dto.PostRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static jdbc.board.utils.JdbcUtils.getPost;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@ActiveProfiles("test")
@EnableConfigurationProperties(CorsProperties.class)
class PostControllerTest {

    public static final String LOCATION = "Location";
    public static final String BASE_URL = "http://localhost";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @Test
    @DisplayName("게시글 목록 조회 - 성공 (페이지 지정)")
    void post_성공() throws Exception {
        int page = 2;
        PostLine postLine1 = new PostLine(1L, "Title 1", 2L);
        PostLine postLine2 = new PostLine(2L, "Title 2", 55L);
        List<PostLine> postLines = Arrays.asList(postLine1, postLine2);
        PageState pageState = new PageState(page, 5, 1, 5, 1, 5);

        when(postService.findAllPostLines(page)).thenReturn(postLines);
        when(postService.findPageState(page)).thenReturn(pageState);

        ResultActions resultActions = mockMvc.perform(get("/post")
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.postLines", hasSize(2))).andExpect(jsonPath("$.postLines[0].postId", is(1))).andExpect(jsonPath("$.postLines[0].postTitle", is("Title 1"))).andExpect(jsonPath("$.pageState.currentPage", is(page))).andExpect(jsonPath("$.pageState.totalPages", is(5)));
    }

    @Test
    void post_적절하지_않은_페이지() throws Exception {
        // given
        int wrongPage = -1;
        when(postService.findAllPostLines(anyInt())).thenThrow(PageOverflowedException.class);

        // when
        ResultActions resultActions = mockMvc.perform(get("/post").param("page", String.valueOf(wrongPage)).contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void getPost_성공() throws Exception {
        // given
        long postId = 1L;
        Post post = getPost(postId, "title", "contents");
        when(postService.findPostDetails(postId)).thenReturn(post);

        // when
        ResultActions resultActions = mockMvc.perform(get("/post/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is((int) postId)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.contents", is("contents")))
                .andExpect(jsonPath("$.comments", hasSize(0)));
    }

    @Test
    void getPost_존재하지_않는_게시글() throws Exception {
        // given
        long postId = -1;
        when(postService.findPostDetails(postId)).thenThrow(PostNotFoundException.class);

        // when
        ResultActions resultActions = mockMvc.perform(get("/post/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void newPost_성공() throws Exception {
        // given
        PostRequestDto postRequestDto = new PostRequestDto();
        String title = "title";
        String contents = "contents";
        postRequestDto.setTitle(title);
        postRequestDto.setContents(contents);
        String jsonString = objectMapper.writeValueAsString(postRequestDto);

        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        Post post = getPost(1L, title, contents);
        when(postService.writePost(postCaptor.capture())).thenReturn(post);

        // when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(header().exists(LOCATION))
                .andExpect(header().string(LOCATION, BASE_URL + "/post/" + post.getId()));
        assertThat(postCaptor.getValue().getTitle()).isEqualTo(title);
        assertThat(postCaptor.getValue().getContents()).isEqualTo(contents);
    }

    @Test
    void newPost_올바르지_않은_서식() throws Exception {
        // given
        PostRequestDto postRequestDto = new PostRequestDto();
        String title = "";
        String contents = "";
        postRequestDto.setTitle(title);
        postRequestDto.setContents(contents);
        String jsonString = objectMapper.writeValueAsString(postRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        // then
        resultActions.andExpect(status().isBadRequest());
    }


    @Test
    void editPost_성공() throws Exception {
        // given
        long postId = 1L;
        String newTitle = "newTitle";
        String prevContents = "contents";

        Post post = getPost(postId, newTitle, prevContents);
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setTitle(newTitle);
        postRequestDto.setContents(prevContents);

        String jsonString = objectMapper.writeValueAsString(postRequestDto);

        when(postService.updatePost(postId, postRequestDto.getTitle(), postRequestDto.getContents())).thenReturn(post);

        // when
        ResultActions resultActions = mockMvc.perform(put("/post/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.postId", is((int) postId)))
                .andExpect(jsonPath("$.title", is(newTitle)))
                .andExpect(jsonPath("$.contents", is(prevContents)));
    }

    @Test
    void editPost_올바르지_않은_서식() throws Exception {
    }

    @Test
    void editPost_존재하지_않는_게시글() throws Exception {

    }

    @Test
    void deletePost_성공() throws Exception {
    }
}

