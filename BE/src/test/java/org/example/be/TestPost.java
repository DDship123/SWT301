package org.example.be;

import org.example.be.entity.*;
import org.example.be.repository.PostRepository;
import org.example.be.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPost {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

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
    public void createVehiclePostFail(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createVehiclePost();
        inputPost.setTitle(""); // Tiêu đề rỗng, giả sử không hợp lệ

        // Mock repository behavior
        when(postRepository.save(any(Post.class))).thenThrow(new RuntimeException("Invalid data"));

        // Act & Assert - Thực hiện hàm cần test và kiểm tra ngoại lệ
        try {
            postService.createPost(inputPost);
        } catch (RuntimeException e) {
            assertEquals("Invalid data", e.getMessage());
        }

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
    public void createBatteryPostFail(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createBatteryPost();
        inputPost.setTitle(null); // Tiêu đề null, giả sử không hợp lệ

        // Mock repository behavior
        when(postRepository.save(any(Post.class))).thenThrow(new RuntimeException("Invalid data"));

        // Act & Assert - Thực hiện hàm cần test và kiểm tra ngoại lệ
        try {
            postService.createPost(inputPost);
        } catch (RuntimeException e) {
            assertEquals("Invalid data", e.getMessage());
        }

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).save(inputPost);
    }

    @Test
    public void updatePost(){
        // Arrange - Chuẩn bị dữ liệu test
        Post existingPost = PostTestDataBuilder.createVehiclePost();
        existingPost.setPostsId(1);

        Post updatedPost = PostTestDataBuilder.createVehiclePost();
        updatedPost.setPostsId(1);
        updatedPost.setTitle("Bán Tesla Model S 2023"); // Cập nhật tiêu đề

        // Mock repository behavior
        when(postRepository.findById(1)).thenReturn(java.util.Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // Act - Thực hiện hàm cần test
        Post result = postService.updatePost(1, updatedPost);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getPostsId());
        assertEquals("Bán Tesla Model S 2023", result.getTitle());

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findById(1);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void updatePostFail(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createVehiclePost();
        inputPost.setPostsId(999); // Giả sử ID không tồn tại

        // Mock repository behavior
        when(postRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // Act - Thực hiện hàm cần test
        Post result = postService.updatePost(999, inputPost);

        // Assert - Kiểm tra kết quả
        assertEquals(null, result);

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findById(999);
    }

    @Test
    public void updatePostStatus() {
        // Arrange - Chuẩn bị dữ liệu test
        Post existingPost = PostTestDataBuilder.createVehiclePost();
        existingPost.setPostsId(1);
        existingPost.setStatus("PENDING");

        Post updatedPost = PostTestDataBuilder.createVehiclePost();
        updatedPost.setPostsId(1);
        updatedPost.setStatus("APPROVED");

        // Mock repository behavior
        when(postRepository.findById(1)).thenReturn(java.util.Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        // Act - Thực hiện hàm cần test
        Post result = postService.updatePost(1, existingPost);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getPostsId());
        assertEquals("APPROVED", result.getStatus());

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findById(1);
    }

    @Test
    public void updatePostStatusFail(){
        // Arrange - Chuẩn bị dữ liệu test
        Post inputPost = PostTestDataBuilder.createVehiclePost();
        inputPost.setPostsId(999); // Giả sử ID không tồn tại

        // Mock repository behavior
        when(postRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // Act - Thực hiện hàm cần test
        Post result = postService.updatePost(999, inputPost);

        // Assert - Kiểm tra kết quả
        assertEquals(null, result);

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findById(999);
    }

    @Test
    public void deletePost(){
        // Arrange - Chuẩn bị dữ liệu test
        Integer postId = 1;

        // Mock repository behavior
        when(postRepository.existsById(postId)).thenReturn(true);
        doNothing().when(postRepository).deleteById(postId);

        // Act - Thực hiện hàm cần test
        boolean result = postService.deletePost(postId);

        // Assert - Kiểm tra kết quả
        assertEquals(true, result);

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).existsById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    public void deletePostFail(){
        // Arrange - Chuẩn bị dữ liệu test
        Integer postId = 999; // Giả sử ID không tồn tại

        // Mock repository behavior
        when(postRepository.existsById(postId)).thenReturn(false);

        // Act - Thực hiện hàm cần test
        boolean result = postService.deletePost(postId);

        // Assert - Kiểm tra kết quả
        assertEquals(false, result);

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).existsById(postId);
    }

    @Test
    public void getAllPostsByStatus(){
        // Arrange - Chuẩn bị dữ liệu test
        String status = "APPROVED";

        // Mock repository behavior
        when(postRepository.findAllByStatusOrderByCreatedAtDesc(status))
                .thenReturn(PostTestDataBuilder.createPostListByStatus(status));

        // Act - Thực hiện hàm cần test
        var result = postService.getAllPostsByStatus(status);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(3, result.size());
        for (Post post : result) {
            assertEquals(status, post.getStatus());
        }

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findAllByStatusOrderByCreatedAtDesc(status);
    }

    @Test
    public void getAllPostsByStatusFail(){
        // Arrange - Chuẩn bị dữ liệu test
        String status = "NON_EXISTENT_STATUS";

        // Mock repository behavior
        when(postRepository.findAllByStatusOrderByCreatedAtDesc(status))
                .thenReturn(java.util.Collections.emptyList());

        // Act - Thực hiện hàm cần test
        var result = postService.getAllPostsByStatus(status);

        // Assert - Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(0, result.size());

        // Verify - Kiểm tra repository đã được gọi
        verify(postRepository, times(1)).findAllByStatusOrderByCreatedAtDesc(status);
    }
}
