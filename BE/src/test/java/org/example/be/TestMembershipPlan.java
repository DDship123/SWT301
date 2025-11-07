package org.example.be;
import org.example.be.dataBuilder.MembershipPlanTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.be.entity.*;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
