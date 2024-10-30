package com.nubble.backend.comment.feature.findallbypost;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nubble.backend.category.board.domain.Board;
import com.nubble.backend.category.board.service.BoardRepository;
import com.nubble.backend.category.domain.Category;
import com.nubble.backend.category.service.CategoryRepository;
import com.nubble.backend.comment.fixture.GuestCommentFixture;
import com.nubble.backend.comment.fixture.MemberCommentFixture;
import com.nubble.backend.comment.domain.guest.GuestComment;
import com.nubble.backend.comment.domain.member.MemberComment;
import com.nubble.backend.comment.feature.findallbypost.FindAllByPostCommentController.FindAllByPostCommentResponse;
import com.nubble.backend.comment.feature.findallbypost.FindAllByPostCommentController.FindByPostCommentResponse;
import com.nubble.backend.comment.repository.CommentRepository;
import com.nubble.backend.post.domain.Post;
import com.nubble.backend.post.repository.PostRepository;
import com.nubble.backend.user.domain.User;
import com.nubble.backend.user.service.UserRepository;
import com.nubble.backend.utils.fixture.domain.BoardFixture;
import com.nubble.backend.utils.fixture.domain.CategoryFixture;
import com.nubble.backend.utils.fixture.domain.PostFixture;
import com.nubble.backend.utils.fixture.domain.UserFixture;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class FindAllByPostCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FindAllByPostCommentMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("게시글의 모든 댓글을 가져온다")
    @Test
    void success() throws Exception {
        // 게시글 생성
        User user = UserFixture.aUser().build();
        userRepository.save(user);

        Category category = CategoryFixture.aCategory().build();
        categoryRepository.save(category);

        Board board = BoardFixture.aBoard()
                .category(category).build();
        boardRepository.save(board);

        Post post = PostFixture.aPublishedPost()
                .board(board)
                .user(user).build();
        postRepository.save(post);

        // 댓글 생성
        MemberComment memberComment = MemberCommentFixture.aMemberComment()
                .user(user)
                .post(post).build();
        GuestComment guestComment = GuestCommentFixture.aGuestComment()
                .post(post).build();
        commentRepository.save(memberComment);
        commentRepository.save(guestComment);
        entityManager.flush();
        entityManager.clear();

        // http request
        MockHttpServletRequestBuilder requestBuilder = get("/posts/{postId}/comments", post.getId());

        // http response
        FindByPostCommentResponse memberCommentResponse = FindByPostCommentResponse.builder()
                .commentId(memberComment.getId())
                .userId(memberComment.getUser().getId())
                .userName(memberComment.getUser().getNickname())
                .content(memberComment.getContent())
                .createdAt(memberComment.getCreatedAt())
                .type("MEMBER").build();
        FindByPostCommentResponse guestCommentResponse = FindByPostCommentResponse.builder()
                .commentId(guestComment.getId())
                .guestName(guestComment.getGuestName())
                .content(guestComment.getContent())
                .createdAt(guestComment.getCreatedAt())
                .type("GUEST").build();
        FindAllByPostCommentResponse response = new FindAllByPostCommentResponse(
                List.of(memberCommentResponse, guestCommentResponse));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print());
    }
}
