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
public class TestMembershipPlan {
    @Mock
    private org.example.be.repository.MembershipPlanRepository membershipPlanRepository;
    @InjectMocks
    private org.example.be.service.MembershipPlanService membershipPlanService;

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
    public void createMembershipPlanFailed() {
        // Arrange
        MembershipPlan plan = MembershipPlanTestDataBuilder.createDefaultMembershipPlan();
        // Mock
        when(membershipPlanRepository.save(any(MembershipPlan.class))).thenThrow(new RuntimeException("DB error"));
        // Act & Assert
        assertThrows(RuntimeException.class, () -> membershipPlanService.createMembershipPlan(plan));
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
    public void getMembershipPlanByMemberIdFailed() {
        // Arrange
        // Mock
        when(membershipPlanRepository.findByMemberId(999)).thenReturn(Optional.empty());
        // Act
        Optional<MembershipPlan> result = membershipPlanService.getMembershipPlanByMemberId(999);
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        // Verify
        verify(membershipPlanRepository, times(1)).findByMemberId(999);
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
    public void updateMembershipPlanFailed() {
        // Arrange
        MembershipPlan updated = MembershipPlanTestDataBuilder.createMembershipPlanWithId(1);
        updated.setName("Nonexistent");
        // Mock
        when(membershipPlanRepository.findById(1)).thenReturn(Optional.empty());
        // Act
        MembershipPlan result = membershipPlanService.updateMembershipPlan(1, updated);
        // Assert
        assertNull(result);
        // Verify
        verify(membershipPlanRepository, times(1)).findById(1);
        verify(membershipPlanRepository, never()).save(any(MembershipPlan.class));
    }
}
