package org.example.be;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.Table;
import org.example.be.entity.*;
import org.example.be.repository.PostRepository;
import org.example.be.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.be.dto.request.MemberRegisterRequest;
import org.example.be.repository.MemberRepository;
import org.example.be.service.MemberService;
import org.example.be.dto.response.ApiResponse;
import org.example.be.dto.request.LoginRequest;
import org.example.be.dto.response.MemberResponse;
import org.example.be.dto.response.ReviewResponse;
import org.example.be.dto.response.CommentResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPost {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private org.example.be.repository.ReviewRepository reviewRepository;
    @InjectMocks
    private org.example.be.service.ReviewService reviewService;

    @Mock
    private org.example.be.repository.MembershipPlanRepository membershipPlanRepository;
    @InjectMocks
    private org.example.be.service.MembershipPlanService membershipPlanService;

    @Mock
    private org.example.be.repository.CommentRepository commentRepository;
    @InjectMocks
    private org.example.be.service.CommentService commentService;

    @Test
    public void createVehiclePost(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createVehiclePost();
        Post savedPost = PostTestDataBuilder.createVehiclePost();
        savedPost.setPostsId(1); // Giả sử ID được tự generate

        // Mock repository behavior
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // Act - Thực hiện hàm cần test
        Post result = postService.createPost(inputPost);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getPostsId());
        assertEquals("Bán Tesla Model 3 2022", result.getTitle());
        assertEquals("VEHICLE", result.getProduct().getProductType());

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).save(inputPost);
    }

    @Test
    public void createBatteryPost(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createBatteryPost();
        Post savedPost = PostTestDataBuilder.createBatteryPost();
        savedPost.setPostsId(1); // Giả sử ID được tự generate

        // Mock repository behavior
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // Act - Thực hiện hàm cần test
        Post result = postService.createPost(inputPost);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getPostsId());
        assertEquals("Pin Lithium 48V Samsung", result.getTitle());
        assertEquals("BATTERY", result.getProduct().getProductType());

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).save(inputPost);
    }

    @Test
    public void registerMember() {
        // Arrange
        MemberRegisterRequest request = MemberTestDataBuilder.createDefaultRegisterRequest();
        Member savedMember = MemberTestDataBuilder.createDefaultMember();
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);
        // Act
        ApiResponse<Member> response = memberService.register(request);
        // Assert
        assertNotNull(response);
        assertEquals(savedMember.getEmail(), response.getPayload().getEmail());
        assertEquals(savedMember.getUsername(), response.getPayload().getUsername());
        assertEquals(savedMember.getPhone(), response.getPayload().getPhone());
        // Verify
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void updateMember() {
        // Arrange
        Integer memberId = 1;
        Member originalMember = MemberTestDataBuilder.createDefaultMember();
        originalMember.setEmail("olduser@example.com");
        originalMember.setUsername("olduser");
        originalMember.setPhone("0912345678");
        Member updatedDetails = MemberTestDataBuilder.createMemberWithId(memberId);
        updatedDetails.setEmail("updateduser@example.com");
        updatedDetails.setUsername("updateduser");
        updatedDetails.setPhone("0912345678");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(originalMember));
        when(memberRepository.findByUsername(updatedDetails.getUsername())).thenReturn(Optional.empty());
        when(memberRepository.findByEmail(updatedDetails.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.findByPhone(updatedDetails.getPhone())).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(updatedDetails);
        // Act
        ApiResponse<Member> response = memberService.updateMember(memberId, updatedDetails);
        // Assert
        assertNotNull(response);
        assertEquals(updatedDetails.getEmail(), response.getPayload().getEmail());
        assertEquals(updatedDetails.getUsername(), response.getPayload().getUsername());
        assertEquals(updatedDetails.getPhone(), response.getPayload().getPhone());
        // Verify
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void loginMember() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        Member member = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.findByUsernameAndPassword("testuser", "password")).thenReturn(Optional.of(member));
        // Act
        Optional<Member> result = memberService.login(request);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(member.getUsername(), result.get().getUsername());
        // Verify
        verify(memberRepository, times(1)).findByUsernameAndPassword("testuser", "password");
    }

    @Test
    public void updateMemberStatus() {
        // Arrange
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.setMemberId(1);
        memberResponse.setStatus("ACTIVE");
        Member member = MemberTestDataBuilder.createDefaultMember();
        member.setStatus("ACTIVE");
        // Mock
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        // Act
        ApiResponse<MemberResponse> response = memberService.updateMemberStatus(memberResponse);
        // Assert
        assertEquals("ACTIVE", response.getPayload().getStatus());
        // Verify
        verify(memberRepository, times(1)).findById(1);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void getMembersByStatus() {
        // Arrange
        Member member = MemberTestDataBuilder.createDefaultMember();
        // Mock
        when(memberRepository.getMembersByStatus("ACTIVE")).thenReturn(List.of(member));
        // Act
        List<Member> result = memberService.getMembersByStatus("ACTIVE");
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(memberRepository, times(1)).getMembersByStatus("ACTIVE");
    }

    @Test
    public void createReview() {
        // Arrange
        Review review = ReviewTestDataBuilder.createDefaultReview();
        // Mock
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        // Act
        Review result = reviewService.createReview(review);
        // Assert
        assertEquals("Great product!", result.getComment());
        // Verify
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void updateReview() {
        // Arrange
        Review original = ReviewTestDataBuilder.createDefaultReview();
        Review updated = ReviewTestDataBuilder.createReviewWithId(1);
        updated.setComment("Updated review");
        // Mock
        when(reviewRepository.findById(1)).thenReturn(Optional.of(original));
        when(reviewRepository.save(any(Review.class))).thenReturn(updated);
        // Act
        Review result = reviewService.updateReview(1, updated);
        // Assert
        assertEquals("Updated review", result.getComment());
        // Verify
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void findAllReviewByStatus() {
        // Arrange
        Review review = ReviewTestDataBuilder.createDefaultReview();
        // Mock
        when(reviewRepository.findAllByStatus("ACTIVE")).thenReturn(List.of(review));
        // Act
        List<ReviewResponse> result = reviewService.findAllReviewByStatus("ACTIVE");
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(reviewRepository, times(1)).findAllByStatus("ACTIVE");
    }

    @Test
    public void createMembershipPlan() {
        // Arrange
        MembershipPlan plan = MembershipPlanTestDataBuilder.createDefaultMembershipPlan();
        // Mock
        when(membershipPlanRepository.save(any(MembershipPlan.class))).thenReturn(plan);
        // Act
        MembershipPlan result = membershipPlanService.createMembershipPlan(plan);
        // Assert
        assertEquals("Gold Plan", result.getName());
        // Verify
        verify(membershipPlanRepository, times(1)).save(any(MembershipPlan.class));
    }

    @Test
    public void getMembershipPlanByMemberId() {
        // Arrange
        MembershipPlan plan = MembershipPlanTestDataBuilder.createDefaultMembershipPlan();
        // Mock
        when(membershipPlanRepository.findByMemberId(1)).thenReturn(Optional.of(plan));
        // Act
        Optional<MembershipPlan> result = membershipPlanService.getMembershipPlanByMemberId(1);
        // Assert
        assertTrue(result.isPresent());
        // Verify
        verify(membershipPlanRepository, times(1)).findByMemberId(1);
    }

    @Test
    public void updateMembershipPlan() {
        // Arrange
        MembershipPlan original = MembershipPlanTestDataBuilder.createDefaultMembershipPlan();
        MembershipPlan updated = MembershipPlanTestDataBuilder.createMembershipPlanWithId(1);
        updated.setName("Platinum Plan");
        // Mock
        when(membershipPlanRepository.findById(1)).thenReturn(Optional.of(original));
        when(membershipPlanRepository.save(any(MembershipPlan.class))).thenReturn(updated);
        // Act
        MembershipPlan result = membershipPlanService.updateMembershipPlan(1, updated);
        // Assert
        assertEquals("Platinum Plan", result.getName());
        // Verify
        verify(membershipPlanRepository, times(1)).findById(1);
        verify(membershipPlanRepository, times(1)).save(any(MembershipPlan.class));
    }

    @Test
    public void createComment() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        // Act
        Comment result = commentService.createComment(comment);
        // Assert
        assertEquals("Nice post!", result.getComment());
        // Verify
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void getAllComments() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAll()).thenReturn(List.of(comment));
        // Act
        List<Comment> result = commentService.getAllComments();
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    public void getCommentById() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        // Act
        Comment result = commentService.getCommentById(1);
        // Assert
        assertEquals("Nice post!", result.getComment());
        // Verify
        verify(commentRepository, times(1)).findById(1);
    }

    @Test
    public void findAllCommentByPostId() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAllByPost_PostsId(1)).thenReturn(List.of(comment));
        // Act
        List<CommentResponse> result = commentService.findAllCommentByPostId(1);
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(commentRepository, times(1)).findAllByPost_PostsId(1);
    }

    @Test
    public void findAllCommentByStatus() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAllByStatus("ACTIVE")).thenReturn(List.of(comment));
        // Act
        List<CommentResponse> result = commentService.findAllCommentByStatus("ACTIVE");
        // Assert
        assertEquals(1, result.size());
        // Verify
        verify(commentRepository, times(1)).findAllByStatus("ACTIVE");
    }
}
