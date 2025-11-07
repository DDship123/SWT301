package org.example.be;

import org.example.be.dataBuilder.CommentTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.be.entity.Comment;
import org.example.be.dto.response.CommentResponse;

@ExtendWith(MockitoExtension.class)
public class TestComment {

    @Mock
    private org.example.be.repository.CommentRepository commentRepository;
    @InjectMocks
    private org.example.be.service.CommentService commentService;



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
    public void createCommentFail() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.save(any(Comment.class))).thenThrow(new RuntimeException("DB error"));
        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.createComment(comment));
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
    public void getAllCommentsFailed() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAll()).thenReturn(List.of());
        // Act
        List<Comment> result = commentService.getAllComments();
        // Assert
        assertEquals(0, result.size());
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
    public void getCommentByIdFailed() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findById(1)).thenThrow(new RuntimeException("Not found"));
        // Act & Assert
        assertThrows(RuntimeException.class, () -> commentService.getCommentById(1));
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
    public void findAllCommentByPostIdFailed() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAllByPost_PostsId(1)).thenReturn(List.of());
        // Act
        List<CommentResponse> result = commentService.findAllCommentByPostId(1);
        // Assert
        assertEquals(0, result.size());
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

    @Test
    public void findAllCommentByStatusFailed() {
        // Arrange
        Comment comment = CommentTestDataBuilder.createDefaultComment();
        // Mock
        when(commentRepository.findAllByStatus("ACTIVE")).thenReturn(List.of());
        // Act
        List<CommentResponse> result = commentService.findAllCommentByStatus("ACTIVE");
        // Assert
        assertEquals(0, result.size());
        // Verify
        verify(commentRepository, times(1)).findAllByStatus("ACTIVE");
    }


    @Test
    public void testCreateCommentFailWrongContent() {
        // This test will fail due to wrong content assertion
        Comment comment = CommentTestDataBuilder.createDefaultComment();

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Comment result = commentService.createComment(comment);

        // This assertion will fail - expecting wrong content
        assertEquals("Wrong content!", result.getComment()); // Expected "Nice post!" but asserting "Wrong content!"

        verify(commentRepository, times(1)).save(any(Comment.class));
    }


}