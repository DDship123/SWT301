package org.example.be;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
