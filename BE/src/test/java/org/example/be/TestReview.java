package org.example.be;

import org.example.be.dataBuilder.ReviewTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.be.entity.Review;
import org.example.be.repository.ReviewRepository;
import org.example.be.service.ReviewService;
import org.example.be.dto.response.ReviewResponse;

@ExtendWith(MockitoExtension.class)
public class TestReview {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

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
    public void createReviewFailed() {
        // Arrange
        Review review = ReviewTestDataBuilder.createDefaultReview();
        // Mock
        when(reviewRepository.save(any(Review.class))).thenThrow(new RuntimeException("DB error"));
        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.createReview(review));
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
    public void updateReviewFailed() {
        // Arrange
        Review updated = ReviewTestDataBuilder.createReviewWithId(1);
        updated.setComment("Will not update");
        // Mock
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());
        // Act
        Review result = reviewService.updateReview(1, updated);
        // Assert
        assertNull(result);
        // Verify
        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, never()).save(any(Review.class));
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
    public void findAllReviewByStatusFailed() {
        // Arrange
        // Mock
        when(reviewRepository.findAllByStatus("ACTIVE")).thenReturn(List.of());
        // Act
        List<ReviewResponse> result = reviewService.findAllReviewByStatus("ACTIVE");
        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        // Verify
        verify(reviewRepository, times(1)).findAllByStatus("ACTIVE");
    }

}